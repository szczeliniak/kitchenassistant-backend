package pl.szczeliniak.kitchenassistant.file

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.file.commands.DeleteFileCommand
import pl.szczeliniak.kitchenassistant.file.commands.UploadFileCommand
import pl.szczeliniak.kitchenassistant.file.queries.DownloadFileCommand
import pl.szczeliniak.kitchenassistant.receipt.FileDao
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.FileFactory

@Configuration
class FileConfiguration {

    @Bean
    fun uploadFileCommand(ftpClient: FtpClient, fileDao: FileDao, fileFactory: FileFactory) =
        UploadFileCommand(ftpClient, fileDao, fileFactory)

    @Bean
    fun downloadFileCommand(ftpClient: FtpClient, fileDao: FileDao) = DownloadFileCommand(ftpClient, fileDao)

    @Bean
    fun deleteFileCommand(ftpClient: FtpClient, fileDao: FileDao) = DeleteFileCommand(ftpClient, fileDao)

}