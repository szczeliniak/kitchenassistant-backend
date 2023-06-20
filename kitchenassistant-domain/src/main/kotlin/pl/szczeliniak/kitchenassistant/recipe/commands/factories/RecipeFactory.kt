package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewRecipeRequest
import pl.szczeliniak.kitchenassistant.recipe.db.AuthorDao
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.TagDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.user.db.UserDao

open class RecipeFactory(
    private val stepFactory: StepFactory,
    private val categoryDao: CategoryDao,
    private val tagDao: TagDao,
    private val tagFactory: TagFactory,
    private val authorDao: AuthorDao,
    private val authorFactory: AuthorFactory,
    private val ingredientGroupFactory: IngredientGroupFactory,
    private val ftpClient: FtpClient,
    private val userDao: UserDao
) {

    open fun create(request: NewRecipeRequest): Recipe {
        val photo = request.photoName?.let {
            if (!ftpClient.exists(it)) {
                throw KitchenAssistantException(ErrorCode.PHOTO_NOT_FOUND)
            }
            it
        }

        return Recipe(0,
            user = userDao.findById(request.userId) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND),
            name = request.name,
            author = request.author?.let {
                authorDao.findByName(it, request.userId) ?: authorDao.save(
                    authorFactory.create(
                        it,
                        request.userId
                    )
                )
            },
            source = request.source,
            category = request.categoryId?.let {
                categoryDao.findById(it) ?: throw KitchenAssistantException(ErrorCode.CATEGORY_NOT_FOUND)
            },
            description = request.description,
            ingredientGroups = request.ingredientGroups.map { ingredientGroupFactory.create(it) }.toMutableSet(),
            steps = request.steps.map { stepFactory.create(it) }.toMutableSet(),
            photoName = photo,
            tags = request.tags.map { tagDao.findByName(it, request.userId) ?: tagFactory.create(it, request.userId) }
                .toMutableSet()
        )
    }

}