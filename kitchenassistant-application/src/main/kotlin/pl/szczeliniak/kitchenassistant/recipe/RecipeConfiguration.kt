package pl.szczeliniak.kitchenassistant.recipe

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.recipe.mapper.*
import pl.szczeliniak.kitchenassistant.shared.FtpClient
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.user.db.UserDao

@Configuration
class RecipeConfiguration {

    @Bean
    fun recipeService(
        recipeDao: RecipeDao,
        categoryDao: CategoryDao,
        tagDao: TagDao,
        authorDao: AuthorDao,
        ingredientDao: IngredientDao,
        stepDao: StepDao,
        userDao: UserDao,
        dayPlanDao: DayPlanDao,
        shoppingListDao: ShoppingListDao,
        ftpClient: FtpClient,
        ingredientGroupDao: IngredientGroupDao,
        requestContext: RequestContext
    ): RecipeService {
        return RecipeService(
            recipeDao,
            ftpClient,
            authorDao,
            dayPlanDao,
            tagDao,
            shoppingListDao,
            categoryDao,
            userDao,
            RecipeMapperImpl(),
            requestContext
        )
    }

    @Bean
    fun tagService(tagDao: TagDao) = TagService(tagDao)

    @Bean
    fun authorService(authorDao: AuthorDao) = AuthorService(authorDao)

    @Bean
    fun stepService(recipeDao: RecipeDao, stepDao: StepDao, ftpClient: FtpClient) =
        StepService(recipeDao, stepDao, ftpClient)

    @Bean
    fun ingredientGroupService(
        recipeDao: RecipeDao,
        ingredientGroupDao: IngredientGroupDao,
        ingredientDao: IngredientDao,
    ) = IngredientGroupService(recipeDao, ingredientGroupDao, ingredientDao, IngredientGroupMapperImpl())

    @Bean
    fun categoryService(
        recipeDao: RecipeDao,
        categoryDao: CategoryDao,
        userDao: UserDao,
        requestContext: RequestContext
    ) = CategoryService(recipeDao, categoryDao, userDao, CategoryMapperImpl(), requestContext)

}