package pl.szczeliniak.kitchenassistant.photo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.shared.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao

@Configuration
class PhotoConfiguration {

    @Bean
    fun photoService(ftpClient: FtpClient, recipeDao: RecipeDao) = PhotoService(ftpClient, recipeDao)

}