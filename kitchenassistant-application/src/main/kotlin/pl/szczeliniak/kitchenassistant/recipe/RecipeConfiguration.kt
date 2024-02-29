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
import pl.szczeliniak.kitchenassistant.recipe.db.TagDao
import pl.szczeliniak.kitchenassistant.recipe.mapper.CategoryMapperImpl
import pl.szczeliniak.kitchenassistant.recipe.mapper.IngredientGroupMapperImpl
import pl.szczeliniak.kitchenassistant.recipe.mapper.RecipeMapperImpl
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
        requestContext: RequestContext
    ): RecipeService {
        return RecipeService(
            recipeDao,
            authorDao,
            dayPlanDao,
            tagDao,
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
    fun stepService(recipeDao: RecipeDao, stepDao: StepDao) =
        StepService(recipeDao, stepDao)

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