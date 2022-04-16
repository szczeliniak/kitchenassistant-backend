package pl.szczeliniak.kitchenassistant.files

import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.exceptions.BadRequestException
import pl.szczeliniak.kitchenassistant.exceptions.FileTooLargeException
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*

@Component
class FtpClientImpl(
    @Value("\${ftp.host}") private val host: String,
    @Value("\${ftp.port}") private val port: Int,
    @Value("\${ftp.user}") private val user: String,
    @Value("\${ftp.password}") private val password: String
) : FtpClient {

    companion object {
        private const val BASE_DIRECTORY = "/photos"
        private const val MAX_BYTES_SIZE = 1000000
        private val logger: Logger = LoggerFactory.getLogger(FtpClient::class.java)
    }

    override fun upload(name: String, content: ByteArray): String {
        logger.info("Uploading file with name: $name")

        if (content.size > MAX_BYTES_SIZE) {
            throw FileTooLargeException("File too large")
        }
        if (SupportedMediaType.byExtension(name) == null) {
            throw BadRequestException("Unsupported file media type")
        }

        val photoName: String = preparePhotoName(name)
        val client = open()
        var inputStream: ByteArrayInputStream? = null
        try {
            inputStream = ByteArrayInputStream(content)
            client.storeFile("$BASE_DIRECTORY/$photoName", inputStream)
        } finally {
            close(client)
            inputStream?.close()
        }

        logger.info("Uploading file with name: $name finished successfully")
        return photoName
    }

    override fun download(name: String): ByteArray {
        logger.info("Downloading file with name: $name")

        if (!exists(name)) {
            throw NotFoundException("File not found")
        }

        val client = open()
        val content: ByteArray
        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            client.retrieveFile("$BASE_DIRECTORY/$name", byteArrayOutputStream)
            content = byteArrayOutputStream.toByteArray()
            byteArrayOutputStream.close()
        } finally {
            close(client)
        }

        logger.info("Downloading file with name: $name finished successfully")
        return content
    }

    override fun delete(name: String) {
        logger.info("Deleting file with name: $name")

        if (!exists(name)) {
            throw NotFoundException("File not found")
        }

        val client = open()
        try {
            client.deleteFile("$BASE_DIRECTORY/$name")
        } finally {
            close(client)
        }

        logger.info("Deleting file with name: $name finished successfully")
    }

    private fun open(): FTPClient {
        val ftpClient = FTPClient()
        ftpClient.connect(host, port)
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE)
        if (!FTPReply.isPositiveCompletion(ftpClient.replyCode)) {
            close(ftpClient)
            throw InterruptedException("Upload exception")
        }
        ftpClient.login(user, password)
        return ftpClient
    }

    private fun close(client: FTPClient) {
        if (client.isConnected) {
            client.logout()
            client.disconnect()
        }
    }

    private fun preparePhotoName(originalFileName: String): String {
        var name = UUID.randomUUID().toString() + "_" + originalFileName
        name = name.replace("\\s".toRegex(), "")
        return if (exists(name)) {
            preparePhotoName(originalFileName)
        } else name
    }

    private fun exists(fileName: String): Boolean {
        val client = open()
        try {
            for (ftpFile in client.listFiles(BASE_DIRECTORY)) {
                if (ftpFile.name == fileName) {
                    return true
                }
            }
        } finally {
            close(client)
        }
        return false
    }

}