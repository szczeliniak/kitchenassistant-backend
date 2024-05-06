package pl.szczeliniak.cookbook.recipe

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.cookbook.dayplan.db.DayPlanDao
import pl.szczeliniak.cookbook.recipe.db.AuthorDao
import pl.szczeliniak.cookbook.recipe.db.CategoryDao
import pl.szczeliniak.cookbook.recipe.db.IngredientDao
import pl.szczeliniak.cookbook.recipe.db.IngredientGroupDao
import pl.szczeliniak.cookbook.recipe.db.RecipeDao
import pl.szczeliniak.cookbook.recipe.db.StepDao
import pl.szczeliniak.cookbook.recipe.db.StepGroupDao
import pl.szczeliniak.cookbook.recipe.db.TagDao
import pl.szczeliniak.cookbook.shared.RequestContext
import pl.szczeliniak.cookbook.user.db.UserDao

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
        ingredientGroupDao: IngredientGroupDao,
        stepGroupDao: StepGroupDao,
        requestContext: RequestContext,
        recipeMapper: RecipeMapper
    ): RecipeService {
        return RecipeService(
            recipeDao,
            authorDao,
            tagDao,
            categoryDao,
            userDao,
            recipeMapper,
            ingredientGroupDao,
            ingredientDao,
            stepGroupDao,
            stepDao,
            requestContext
        )
    }

    @Bean
    fun tagService(tagDao: TagDao, requestContext: RequestContext) = TagService(tagDao, requestContext)

    @Bean
    fun authorService(authorDao: AuthorDao, requestContext: RequestContext) = AuthorService(authorDao, requestContext)

    @Bean
    fun categoryService(
        recipeDao: RecipeDao,
        categoryDao: CategoryDao,
        userDao: UserDao,
        requestContext: RequestContext,
        recipeMapper: RecipeMapper
    ) = CategoryService(recipeDao, categoryDao, userDao, recipeMapper, requestContext)

    @Bean
    fun recipeMapper() = RecipeMapperImpl()

}