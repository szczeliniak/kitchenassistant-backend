package pl.szczeliniak.kitchenassistant.recipe

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.user.db.UserDao

@Configuration
class RecipeConfiguration {

    @Bean
    fun recipeMapper() = RecipeMapperImpl()

    @Bean
    fun ingredientGroupMapper() = IngredientGroupMapperImpl()

    @Bean
    fun categoryMapper() = CategoryMapperImpl()

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
        recipeMapper: RecipeMapper,
        ingredientGroupMapper: IngredientGroupMapper,
        categoryMapper: CategoryMapper
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
            recipeMapper
        )
    }

    @Bean
    fun tagService(tagDao: TagDao) = TagService(tagDao)

    @Bean
    fun authorService(authorDao: AuthorDao) = AuthorService(authorDao)

    @Bean
    fun stepService(recipeDao: RecipeDao, stepDao: StepDao) = StepService(recipeDao, stepDao)

    @Bean
    fun ingredientGroupService(
        recipeDao: RecipeDao,
        ingredientGroupDao: IngredientGroupDao,
        ingredientDao: IngredientDao,
        ingredientGroupMapper: IngredientGroupMapper
    ) = IngredientGroupService(recipeDao, ingredientGroupDao, ingredientDao, ingredientGroupMapper)

    @Bean
    fun categoryService(
        recipeDao: RecipeDao,
        categoryDao: CategoryDao,
        userDao: UserDao,
        categoryMapper: CategoryMapper
    ) = CategoryService(recipeDao, categoryDao, userDao, categoryMapper)

}