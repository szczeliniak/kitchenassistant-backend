package pl.szczeliniak.kitchenassistant.photo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao

@Configuration
class PhotoConfiguration {

    @Bean
    fun photoFacade(ftpClient: FtpClient, recipeDao: RecipeDao) = PhotoService(ftpClient, recipeDao)

}