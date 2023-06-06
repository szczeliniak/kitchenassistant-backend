package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.UpdateRecipeDto
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

    fun execute(id: Int, dto: UpdateRecipeDto): SuccessResponse {
        val recipe =
            recipeDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)

        dto.photoName?.let {
            if (!ftpClient.exists(it)) {
                throw KitchenAssistantException(ErrorCode.FTP_FILE_NOT_FOUND)
            }
        }

        recipe.name = dto.name
        recipe.description = dto.description
        recipe.category = getCategory(recipe.category, dto.categoryId)
        recipe.source = dto.source
        recipe.author = dto.author?.let {
            authorDao.findByName(it, recipe.user.id) ?: authorDao.save(
                authorFactory.create(
                    it,
                    recipe.user
                )
            )
        }
        recipe.photoName = dto.photoName
        recipe.tags = dto.tags.map {
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