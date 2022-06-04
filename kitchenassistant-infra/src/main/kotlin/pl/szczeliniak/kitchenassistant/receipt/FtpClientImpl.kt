package pl.szczeliniak.kitchenassistant.receipt

import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.shared.exceptions.BadRequestException
import pl.szczeliniak.kitchenassistant.shared.exceptions.FileTooLargeException
import pl.szczeliniak.kitchenassistant.shared.exceptions.FtpException
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

@Component
class FtpClientImpl(
    @Value("\${ftp.host}") private val host: String,
    @Value("\${ftp.port}") private val port: Int,
    @Value("\${ftp.user}") private val user: String,
    @Value("\${ftp.password}") private val password: String
) : FtpClient {

    companion object {
        private const val BASE_DIRECTORY = "/files"
        private const val MAX_BYTES_SIZE = 1000000
        private val logger: Logger = LoggerFactory.getLogger(FtpClient::class.java)
    }

    override fun upload(name: String, content: ByteArray): String {
        if (content.size > MAX_BYTES_SIZE) {
            throw FileTooLargeException("File too large")
        }
        if (SupportedMediaType.byFileName(name) == null) {
            throw BadRequestException("Unsupported file media type")
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
            throw FtpException(exception.message ?: "Error while file upload")
        } finally {
            close(client)
            inputStream?.close()
        }

        logger.info("Uploading file with name: $name finished successfully")
        return photoName
    }

    override fun download(name: String): ByteArray {
        logger.info("Downloading file with name: $name")
        val client = open()

        if (!exists(client, name)) {
            throw NotFoundException("File $name not found")
        }

        val content: ByteArray
        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            client.retrieveFile("$BASE_DIRECTORY/$name", byteArrayOutputStream)
            content = byteArrayOutputStream.toByteArray()
            byteArrayOutputStream.close()
        } catch (exception: IOException) {
            logger.error(exception.message ?: "Error while file download", exception)
            throw FtpException(exception.message ?: "Error while file download")
        } finally {
            close(client)
        }

        logger.info("Downloading file with name: $name finished successfully")
        return content
    }

    override fun delete(name: String) {
        logger.info("Deleting file with name: $name")
        val client = open()

        if (!exists(client, name)) {
            throw NotFoundException("File $name not found")
        }

        try {
            client.deleteFile("$BASE_DIRECTORY/$name")
        } catch (exception: IOException) {
            logger.error(exception.message ?: "Error while file delete", exception)
            throw FtpException(exception.message ?: "Error while file delete")
        } finally {
            close(client)
        }

        logger.info("Deleting file with name: $name finished successfully")
    }

    private fun open(): FTPClient {
        val ftpClient = FTPClient()
        ftpClient.connect(host, port)
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE)
        ftpClient.login(user, password)
        if (!FTPReply.isPositiveCompletion(ftpClient.replyCode)) {
            val code = ftpClient.replyCode
            close(ftpClient)
            throw FtpException("FTP connection error. Code: $code")
        }
        return ftpClient
    }

    private fun close(client: FTPClient) {
        if (client.isConnected) {
            client.logout()
            client.disconnect()
        }
    }

    private fun preparePhotoName(client: FTPClient, originalFileName: String): String {
        var name = UUID.randomUUID().toString() + "_" + originalFileName
        name = name.replace("\\s".toRegex(), "")
        return if (exists(client, name)) {
            preparePhotoName(client, originalFileName)
        } else name
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
            throw FtpException(exception.message ?: "Error while file exist check")
        }
        return false
    }

}