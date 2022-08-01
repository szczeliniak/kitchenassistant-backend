package pl.szczeliniak.kitchenassistant.recipe

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.dayplan.commands.DeassignRecipesFromDayPlansCommand
import pl.szczeliniak.kitchenassistant.recipe.commands.*
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.*
import pl.szczeliniak.kitchenassistant.recipe.queries.*
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.DeassignRecipeFromShoppingListsCommand
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery

@Configuration
class RecipeConfiguration {

    @Bean
    fun recipeConverter() = RecipeConverter()

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
        photoDao: PhotoDao,
        ingredientDao: IngredientDao,
        stepDao: StepDao,
        ftpClient: FtpClient,
        ingredientGroupDao: IngredientGroupDao,
        deassignRecipesFromDayPlansCommand: DeassignRecipesFromDayPlansCommand,
        deassignRecipeFromShoppingListsCommand: DeassignRecipeFromShoppingListsCommand,
        recipeConverter: RecipeConverter,
        getRecipeQuery: GetRecipeQuery
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
            photoDao,
            ingredientGroupFactory
        )
        val photoFactory = PhotoFactory()
        return RecipeFacadeImpl(
            getRecipeQuery,
            GetRecipesQuery(recipeDao, recipeConverter),
            AddRecipeCommand(recipeDao, recipeFactory),
            AddCategoryCommand(categoryDao, categoryFactory),
            DeleteRecipeCommand(
                recipeDao,
                deassignRecipesFromDayPlansCommand,
                deassignRecipeFromShoppingListsCommand
            ),
            UpdateRecipeCommand(recipeDao, categoryDao, tagDao, tagFactory, authorFactory, authorDao),
            AddIngredientCommand(recipeDao, ingredientDao, ingredientFactory),
            DeleteIngredientCommand(recipeDao),
            UpdateIngredientCommand(recipeDao),
            AddStepCommand(recipeDao, stepDao, stepFactory),
            DeleteStepCommand(recipeDao),
            DeleteCategoryCommand(categoryDao, recipeDao),
            UpdateCategoryCommand(categoryDao),
            UpdateStepCommand(recipeDao),
            GetCategoriesQuery(categoryDao, recipeConverter),
            AssignRecipePhotosCommand(recipeDao, photoDao),
            GetTagsQuery(tagDao),
            MarkRecipeAsFavoriteCommand(recipeDao),
            GetAuthorsQuery(authorDao),
            UploadPhotoCommand(ftpClient, photoDao, photoFactory),
            DeletePhotoCommand(ftpClient, photoDao),
            DownloadPhotoQuery(ftpClient, photoDao),
            AddIngredientGroupCommand(recipeDao, ingredientGroupFactory, ingredientGroupDao),
            DeleteIngredientGroupCommand(recipeDao)
        )
    }

}