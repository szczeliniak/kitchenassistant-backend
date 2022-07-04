package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.dayplan.commands.DeassignReceiptsFromDayPlansCommand
import pl.szczeliniak.kitchenassistant.receipt.commands.*
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.*
import pl.szczeliniak.kitchenassistant.receipt.queries.*
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.DeassignReceiptFromShoppingListsCommand
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery

@Configuration
class ReceiptConfiguration {

    @Bean
    fun receiptFacade(
        getUserByIdQuery: GetUserByIdQuery,
        receiptDao: ReceiptDao,
        categoryDao: CategoryDao,
        tagDao: TagDao,
        authorDao: AuthorDao,
        photoDao: PhotoDao,
        ingredientDao: IngredientDao,
        stepDao: StepDao,
        ftpClient: FtpClient,
        ingredientGroupDao: IngredientGroupDao,
        deassignReceiptsFromDayPlansCommand: DeassignReceiptsFromDayPlansCommand,
        deassignReceiptFromShoppingListsCommand: DeassignReceiptFromShoppingListsCommand
    ): ReceiptFacade {
        val receiptConverter = ReceiptConverter()
        val stepFactory = StepFactory()
        val tagFactory = TagFactory()
        val authorFactory = AuthorFactory()
        val categoryFactory = CategoryFactory()
        val ingredientFactory = IngredientFactory()
        val ingredientGroupFactory = IngredientGroupFactory(ingredientFactory)
        val receiptFactory = ReceiptFactory(
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
        return ReceiptFacadeImpl(
            GetReceiptQuery(receiptDao, receiptConverter),
            GetReceiptsQuery(receiptDao, receiptConverter),
            AddReceiptCommand(receiptDao, receiptFactory),
            AddCategoryCommand(categoryDao, categoryFactory),
            DeleteReceiptCommand(
                receiptDao,
                deassignReceiptsFromDayPlansCommand,
                deassignReceiptFromShoppingListsCommand
            ),
            UpdateReceiptCommand(receiptDao, categoryDao, tagDao, tagFactory, authorFactory, authorDao),
            AddIngredientCommand(receiptDao, ingredientDao, ingredientFactory),
            DeleteIngredientCommand(receiptDao),
            UpdateIngredientCommand(receiptDao),
            AddStepCommand(receiptDao, stepDao, stepFactory),
            DeleteStepCommand(receiptDao),
            DeleteCategoryCommand(categoryDao, receiptDao),
            UpdateCategoryCommand(categoryDao),
            UpdateStepCommand(receiptDao),
            GetCategoriesQuery(categoryDao, receiptConverter),
            AssignReceiptPhotosCommand(receiptDao, photoDao),
            GetTagsQuery(tagDao),
            MarkReceiptAsFavoriteCommand(receiptDao),
            GetAuthorsQuery(authorDao),
            UploadPhotoCommand(ftpClient, photoDao, photoFactory),
            DeletePhotoCommand(ftpClient, photoDao),
            DownloadPhotoQuery(ftpClient, photoDao),
            AddIngredientGroupCommand(receiptDao, ingredientGroupFactory, ingredientGroupDao),
            DeleteIngredientGroupCommand(receiptDao)
        )
    }

}