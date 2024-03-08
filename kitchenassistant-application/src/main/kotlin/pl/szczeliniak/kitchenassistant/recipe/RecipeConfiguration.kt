package pl.szczeliniak.kitchenassistant.recipe

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.recipe.db.AuthorDao
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientDao
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroupDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.db.StepDao
import pl.szczeliniak.kitchenassistant.recipe.db.StepGroupDao
import pl.szczeliniak.kitchenassistant.recipe.db.TagDao
import pl.szczeliniak.kitchenassistant.shared.RequestContext
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
        ingredientGroupDao: IngredientGroupDao,
        stepGroupDao: StepGroupDao,
        requestContext: RequestContext
    ): RecipeService {
        return RecipeService(
            recipeDao,
            authorDao,
            tagDao,
            categoryDao,
            userDao,
            RecipeMapperImpl(),
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
        requestContext: RequestContext
    ) = CategoryService(recipeDao, categoryDao, userDao, CategoryMapperImpl(), requestContext)

}