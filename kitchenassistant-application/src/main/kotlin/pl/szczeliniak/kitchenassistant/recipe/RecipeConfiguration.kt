package pl.szczeliniak.kitchenassistant.recipe

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.dayplan.commands.DeleteRecipeFromDayPlansCommand
import pl.szczeliniak.kitchenassistant.recipe.commands.*
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.*
import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.recipe.queries.*
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.DeleteRecipeFromShoppingListsCommand
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery

@Configuration
class RecipeConfiguration {

    @Bean
    fun ingredientGroupConverter() = IngredientGroupConverter()

    @Bean
    fun recipeConverter(ingredientGroupConverter: IngredientGroupConverter) = RecipeConverter(ingredientGroupConverter)

    @Bean
    fun getRecipeQuery(recipeDao: RecipeDao, recipeConverter: RecipeConverter) =
            GetRecipeQuery(recipeDao, recipeConverter)

    @Bean
    fun recipeFacade(
            getUserByIdQuery: GetUserByIdQuery,
            recipeDao: RecipeDao,
            categoryDao: CategoryDao,
            tagDao: TagDao,
            authorDao: AuthorDao,
            ingredientDao: IngredientDao,
            stepDao: StepDao,
            ftpClient: FtpClient,
            ingredientGroupDao: IngredientGroupDao,
            deleteRecipeFromDayPlansCommand: DeleteRecipeFromDayPlansCommand,
            deleteRecipeFromShoppingListsCommand: DeleteRecipeFromShoppingListsCommand,
            recipeConverter: RecipeConverter,
            getRecipeQuery: GetRecipeQuery,
            ingredientGroupConverter: IngredientGroupConverter
    ): RecipeFacade {
        val stepFactory = StepFactory()
        val tagFactory = TagFactory()
        val authorFactory = AuthorFactory()
        val categoryFactory = CategoryFactory()
        val ingredientFactory = IngredientFactory()
        val ingredientGroupFactory = IngredientGroupFactory(ingredientFactory)
        val recipeFactory = RecipeFactory(
                getUserByIdQuery,
                stepFactory,
                categoryDao,
                tagDao,
                tagFactory,
                authorDao,
                authorFactory,
                ingredientGroupFactory, ftpClient
        )
        return RecipeFacade(
                getRecipeQuery,
                GetRecipesQuery(recipeDao, recipeConverter),
                AddRecipeCommand(recipeDao, recipeFactory),
                AddCategoryCommand(categoryDao, categoryFactory),
                DeleteRecipeCommand(
                        recipeDao,
                        deleteRecipeFromDayPlansCommand,
                        deleteRecipeFromShoppingListsCommand
                ),
                UpdateRecipeCommand(recipeDao, categoryDao, tagDao, tagFactory, authorFactory, authorDao),
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