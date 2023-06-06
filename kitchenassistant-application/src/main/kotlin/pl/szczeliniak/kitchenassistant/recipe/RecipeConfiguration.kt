package pl.szczeliniak.kitchenassistant.recipe

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.recipe.commands.*
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.*
import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.recipe.queries.*
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.user.db.UserDao

@Configuration
class RecipeConfiguration {

    @Bean
    fun recipeFacade(
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
        ingredientGroupDao: IngredientGroupDao
    ): RecipeFacade {
        val stepFactory = StepFactory()
        val tagFactory = TagFactory(userDao)
        val authorFactory = AuthorFactory(userDao)
        val categoryFactory = CategoryFactory(userDao)
        val ingredientFactory = IngredientFactory()
        val ingredientGroupFactory = IngredientGroupFactory(ingredientFactory)
        val recipeFactory = RecipeFactory(
            stepFactory,
            categoryDao,
            tagDao,
            tagFactory,
            authorDao,
            authorFactory,
            ingredientGroupFactory, ftpClient, userDao
        )
        val ingredientGroupConverter = IngredientGroupConverter()
        val recipeConverter = RecipeConverter(ingredientGroupConverter)
        return RecipeFacade(
            GetRecipeQuery(recipeDao, recipeConverter),
            GetRecipesQuery(recipeDao, recipeConverter),
            AddRecipeCommand(recipeDao, recipeFactory),
            AddCategoryCommand(categoryDao, categoryFactory),
            DeleteRecipeCommand(recipeDao, dayPlanDao, shoppingListDao),
            UpdateRecipeCommand(recipeDao, categoryDao, tagDao, tagFactory, authorFactory, authorDao, ftpClient),
            AddStepCommand(recipeDao, stepDao, stepFactory),
            DeleteStepCommand(recipeDao),
            DeleteCategoryCommand(categoryDao, recipeDao),
            UpdateCategoryCommand(categoryDao),
            UpdateStepCommand(recipeDao),
            GetCategoriesQuery(categoryDao, recipeConverter),
            GetTagsQuery(tagDao),
            MarkRecipeAsFavoriteCommand(recipeDao),
            GetAuthorsQuery(authorDao),
            AddIngredientGroupCommand(recipeDao, ingredientGroupFactory, ingredientGroupDao),
            UpdateIngredientGroupCommand(recipeDao, ingredientGroupDao, ingredientDao, ingredientFactory),
            DeleteIngredientGroupCommand(recipeDao, ingredientGroupDao, ingredientDao),
            DeleteIngredientCommand(recipeDao, ingredientDao),
            GetIngredientGroupQuery(recipeDao, ingredientGroupConverter)
        )
    }

}