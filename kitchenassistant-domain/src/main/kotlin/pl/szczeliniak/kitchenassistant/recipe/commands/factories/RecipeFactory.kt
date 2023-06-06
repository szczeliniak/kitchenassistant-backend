package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewRecipeDto
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

    open fun create(dto: NewRecipeDto): Recipe {
        val photo = dto.photoName?.let {
            if (!ftpClient.exists(it)) {
                throw KitchenAssistantException(ErrorCode.PHOTO_NOT_FOUND)
            }
            it
        }

        return Recipe(0,
            user = userDao.findById(dto.userId) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND),
            name = dto.name,
            author = dto.author?.let {
                authorDao.findByName(it, dto.userId) ?: authorDao.save(
                    authorFactory.create(
                        it,
                        dto.userId
                    )
                )
            },
            source = dto.source,
            category = dto.categoryId?.let {
                categoryDao.findById(it) ?: throw KitchenAssistantException(ErrorCode.CATEGORY_NOT_FOUND)
            },
            description = dto.description,
            ingredientGroups = dto.ingredientGroups.map { ingredientGroupFactory.create(it) }.toMutableSet(),
            steps = dto.steps.map { stepFactory.create(it) }.toMutableSet(),
            photoName = photo,
            tags = dto.tags.map { tagDao.findByName(it, dto.userId) ?: tagFactory.create(it, dto.userId) }
                .toMutableSet()
        )
    }

}