package pl.szczeliniak.kitchenassistant.files

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.files.commands.DeletePhotoFileCommand
import pl.szczeliniak.kitchenassistant.files.commands.UploadPhotoFileCommand
import pl.szczeliniak.kitchenassistant.files.queries.DownloadPhotoFileCommand

@Configuration
class FileConfiguration {

    @Bean
    fun uploadPhotoFileCommand(ftpClient: FtpClient) = UploadPhotoFileCommand(ftpClient)

    @Bean
    fun downloadPhotoFileCommand(ftpClient: FtpClient) = DownloadPhotoFileCommand(ftpClient)

    @Bean
    fun deletePhotoFileCommand(ftpClient: FtpClient) = DeletePhotoFileCommand(ftpClient)

}