package pl.szczeliniak.kitchenassistant.file

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.file.commands.DeleteFileCommand
import pl.szczeliniak.kitchenassistant.file.commands.UploadFileCommand
import pl.szczeliniak.kitchenassistant.file.commands.factories.FileFactory
import pl.szczeliniak.kitchenassistant.file.queries.CheckIfFileExistsQuery
import pl.szczeliniak.kitchenassistant.file.queries.DownloadFileQuery

@Configuration
class FileConfiguration {

    @Bean
    fun uploadFileCommand(ftpClient: FtpClient, fileDao: FileDao, fileFactory: FileFactory) =
        UploadFileCommand(ftpClient, fileDao, fileFactory)

    @Bean
    fun downloadFileCommand(ftpClient: FtpClient, fileDao: FileDao) = DownloadFileQuery(ftpClient, fileDao)

    @Bean
    fun deleteFileCommand(ftpClient: FtpClient, fileDao: FileDao) = DeleteFileCommand(ftpClient, fileDao)

    @Bean
    fun checkIfFileExistsQuery(fileDao: FileDao) = CheckIfFileExistsQuery(fileDao)

    @Bean
    fun fileFactory(): FileFactory = FileFactory()

}