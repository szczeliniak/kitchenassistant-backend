package pl.szczeliniak.kitchenassistant

import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.shared.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.SupportedMediaType
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

@Component
class FtpClientImpl(
    @Value("\${ftp.host}") private val host: String,
    @Value("\${ftp.port}") private val port: Int,
    @Value("\${ftp.user}") private val user: String,
    @Value("\${ftp.password}") private val password: String,
    private val environment: Environment
) : FtpClient {

    companion object {
        private const val BASE_DIRECTORY = "/files"
        private const val MAX_BYTES_SIZE = 5000000
        private val logger: Logger = LoggerFactory.getLogger(FtpClient::class.java)
    }

    override fun upload(name: String, content: ByteArray): String {
        if (content.size > MAX_BYTES_SIZE) {
            throw KitchenAssistantException(ErrorCode.FILE_TOO_LARGE)
        }
        if (SupportedMediaType.byFileName(name) == null) {
            throw KitchenAssistantException(ErrorCode.UNSUPPORTED_MEDIA_TYPE)
        }

        logger.info("Uploading file with name: $name")
        val client = open()

        val photoName: String = preparePhotoName(client, name)
        var inputStream: ByteArrayInputStream? = null
        try {
            inputStream = ByteArrayInputStream(content)
            client.makeDirectory(BASE_DIRECTORY)
            client.storeFile("$BASE_DIRECTORY/$photoName", inputStream)
        } catch (exception: IOException) {
            logger.error(exception.message ?: "Error while file upload", exception)
            throw KitchenAssistantException(ErrorCode.FTP_UPLOAD_ERROR)
        } finally {
            close(client)
            inputStream?.close()
        }

        logger.info("Uploading file with name: $name finished successfully")
        return photoName
    }

    override fun download(name: String): ByteArray {
        logger.info("Downloading file with name: $name")

        var client: FTPClient? = null
        val content: ByteArray
        try {
            client = open()
            if (!exists(client, name)) {
                throw KitchenAssistantException(ErrorCode.FTP_FILE_NOT_FOUND)
            }
            val byteArrayOutputStream = ByteArrayOutputStream()
            client.retrieveFile("$BASE_DIRECTORY/$name", byteArrayOutputStream)
            content = byteArrayOutputStream.toByteArray()
            byteArrayOutputStream.close()
        } catch (exception: IOException) {
            logger.error(exception.message ?: "Error while file download", exception)
            throw KitchenAssistantException(ErrorCode.FTP_DOWNLOAD_ERROR)
        } finally {
            close(client)
        }

        logger.info("Downloading file with name: $name finished successfully")
        return content
    }

    override fun delete(name: String) {
        logger.info("Deleting file with name: $name")

        var client: FTPClient? = null
        try {
            client = open()
            if (!exists(client, name)) {
                throw KitchenAssistantException(ErrorCode.FTP_FILE_NOT_FOUND)
            }
            client.deleteFile("$BASE_DIRECTORY/$name")
        } catch (exception: IOException) {
            logger.error(exception.message ?: "Error while file delete", exception)
            throw KitchenAssistantException(ErrorCode.FTP_DELETE_ERROR)
        } finally {
            close(client)
        }

        logger.info("Deleting file with name: $name finished successfully")
    }

    override fun exists(name: String): Boolean {
        logger.info("Checking if file with name: $name exists")

        var client: FTPClient? = null
        val result: Boolean
        try {
            client = open()
            result = exists(client, name)
        } catch (exception: IOException) {
            logger.error(exception.message ?: "Error while checking if file with name: $name exists", exception)
            throw KitchenAssistantException(ErrorCode.FTP_DELETE_ERROR)
        } finally {
            close(client)
        }

        logger.info("Checking if file with name: $name exists finished successfully. Result $result")

        return result
    }

    private fun open(): FTPClient {
        val ftpClient = FTPClient()
        ftpClient.connect(host, port)
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE)
        if (environment.activeProfiles.contains("dev")) {
            ftpClient.enterLocalPassiveMode()
        } else {
            ftpClient.enterRemotePassiveMode()
        }
        ftpClient.login(user, password)
        if (!FTPReply.isPositiveCompletion(ftpClient.replyCode)) {
            close(ftpClient)
            throw KitchenAssistantException(ErrorCode.FTP_GENERIC_ERROR)
        }
        return ftpClient
    }

    private fun close(client: FTPClient?) {
        if (client != null && client.isConnected) {
            client.logout()
            client.disconnect()
        }
    }

    private fun preparePhotoName(client: FTPClient, originalFileName: String): String {
        var name = UUID.randomUUID().toString() + getExtension(originalFileName)
        name = name.replace("\\s".toRegex(), "")
        return if (exists(client, name)) {
            preparePhotoName(client, originalFileName)
        } else name
    }

    private fun getExtension(originalFileName: String): String {
        val split = originalFileName.split(".")
        if (split.isEmpty()) {
            return ""
        }
        return "." + split.last()
    }

    private fun exists(client: FTPClient, fileName: String): Boolean {
        try {
            for (ftpFile in client.listFiles(BASE_DIRECTORY)) {
                if (ftpFile.name == fileName) {
                    return true
                }
            }
        } catch (exception: IOException) {
            logger.error(exception.message ?: "Error while file exist check", exception)
            throw KitchenAssistantException(ErrorCode.FTP_FILE_CHECK_ERROR)
        }
        return false
    }

}