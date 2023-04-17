package pl.szczeliniak.kitchenassistant.photo.commands

import pl.szczeliniak.kitchenassistant.photo.commands.dto.DeletePhotoResponse
import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao

class DeletePhotoCommand(private val recipeDao: RecipeDao, private val ftpClient: FtpClient) {

    fun execute(fileName: String): DeletePhotoResponse {
        val recipes = recipeDao.findAll(RecipeCriteria(false, null, null, null, null, fileName), 0, 1000)
        recipes.forEach {
            it.photoName = null
        }
        recipeDao.save(recipes)
        ftpClient.delete(fileName)

        return DeletePhotoResponse(fileName)
    }

}