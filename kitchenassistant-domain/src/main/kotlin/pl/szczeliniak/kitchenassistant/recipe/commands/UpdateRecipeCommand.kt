package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.UpdateRecipeRequest
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.AuthorFactory
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.TagFactory
import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class UpdateRecipeCommand(
    private val recipeDao: RecipeDao,
    private val categoryDao: CategoryDao,
    private val tagDao: TagDao,
    private val tagFactory: TagFactory,
    private val authorFactory: AuthorFactory,
    private val authorDao: AuthorDao,
    private val ftpClient: FtpClient
) {

    fun execute(id: Int, request: UpdateRecipeRequest): SuccessResponse {
        val recipe =
            recipeDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)

        request.photoName?.let {
            if (!ftpClient.exists(it)) {
                throw KitchenAssistantException(ErrorCode.FTP_FILE_NOT_FOUND)
            }
        }

        recipe.name = request.name
        recipe.description = request.description
        recipe.category = getCategory(recipe.category, request.categoryId)
        recipe.source = request.source
        recipe.author = request.author?.let {
            authorDao.findByName(it, recipe.user.id) ?: authorDao.save(
                authorFactory.create(
                    it,
                    recipe.user
                )
            )
        }
        recipe.photoName = request.photoName
        recipe.tags = request.tags.map {
            recipe.tags.firstOrNull { tag -> it == tag.name } ?: tagDao.findByName(it, recipe.user.id) ?: tagDao.save(
                tagFactory.create(it, recipe.user)
            )
        }.toMutableSet()

        return SuccessResponse(recipeDao.save(recipe).id)
    }

    private fun getCategory(category: Category?, categoryId: Int?): Category? {
        if (category != null && categoryId != null && category.id == categoryId) {
            return category
        }
        return categoryId?.let {
            categoryDao.findById(it) ?: throw KitchenAssistantException(ErrorCode.CATEGORY_NOT_FOUND)
        }
    }

}