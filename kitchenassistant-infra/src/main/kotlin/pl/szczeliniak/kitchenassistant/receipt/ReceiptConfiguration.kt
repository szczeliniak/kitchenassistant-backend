package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.receipt.commands.*
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.*
import pl.szczeliniak.kitchenassistant.receipt.queries.*
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery

@Configuration
class ReceiptConfiguration {

    @Bean
    fun receiptConverter(): ReceiptConverter = ReceiptConverter()

    @Bean
    fun getReceiptQuery(receiptDao: ReceiptDao, receiptConverter: ReceiptConverter): GetReceiptQuery =
        GetReceiptQuery(receiptDao, receiptConverter)

    @Bean
    fun getReceiptsQuery(receiptDao: ReceiptDao, receiptConverter: ReceiptConverter): GetReceiptsQuery =
        GetReceiptsQuery(receiptDao, receiptConverter)

    @Bean
    fun addReceiptCommand(receiptDao: ReceiptDao, receiptFactory: ReceiptFactory): AddReceiptCommand =
        AddReceiptCommand(receiptDao, receiptFactory)

    @Bean
    fun updateReceiptCommand(
        receiptDao: ReceiptDao,
        categoryDao: CategoryDao,
        tagDao: TagDao,
        tagFactory: TagFactory,
        authorFactory: AuthorFactory,
        authorDao: AuthorDao
    ): UpdateReceiptCommand =
        UpdateReceiptCommand(
            receiptDao,
            categoryDao,
            tagDao,
            tagFactory,
            authorFactory,
            authorDao
        )

    @Bean
    fun addIngredientCommand(
        receiptDao: ReceiptDao,
        ingredientDao: IngredientDao,
        ingredientFactory: IngredientFactory,
    ): AddIngredientCommand =
        AddIngredientCommand(receiptDao, ingredientDao, ingredientFactory)

    @Bean
    fun updateIngredientCommand(receiptDao: ReceiptDao): UpdateIngredientCommand = UpdateIngredientCommand(receiptDao)

    @Bean
    fun addStepCommand(receiptDao: ReceiptDao, stepDao: StepDao, stepFactory: StepFactory): AddStepCommand =
        AddStepCommand(receiptDao, stepDao, stepFactory)

    @Bean
    fun updateStepCommand(receiptDao: ReceiptDao): UpdateStepCommand = UpdateStepCommand(receiptDao)

    @Bean
    fun deleteReceiptCommand(receiptDao: ReceiptDao): DeleteReceiptCommand = DeleteReceiptCommand(receiptDao)

    @Bean
    fun deleteStepCommand(receiptDao: ReceiptDao): DeleteStepCommand = DeleteStepCommand(receiptDao)

    @Bean
    fun deleteIngredientCommand(receiptDao: ReceiptDao): DeleteIngredientCommand =
        DeleteIngredientCommand(receiptDao)

    @Bean
    fun stepFactory(): StepFactory = StepFactory()

    @Bean
    fun ingredientFactory(): IngredientFactory = IngredientFactory()

    @Bean
    fun categoryFactory(): CategoryFactory = CategoryFactory()

    @Bean
    fun photoFactory(): PhotoFactory = PhotoFactory()

    @Bean
    fun tagFactory(): TagFactory = TagFactory()

    @Bean
    fun authorFactory(): AuthorFactory = AuthorFactory()

    @Bean
    fun ingredientGroupFactory(ingredientFactory: IngredientFactory): IngredientGroupFactory =
        IngredientGroupFactory(ingredientFactory)

    @Bean
    fun addCategoryCommand(categoryDao: CategoryDao, categoryFactory: CategoryFactory): AddCategoryCommand =
        AddCategoryCommand(categoryDao, categoryFactory)

    @Bean
    fun deleteCategoryCommand(categoryDao: CategoryDao): DeleteCategoryCommand = DeleteCategoryCommand(categoryDao)

    @Bean
    fun updateCategoryCommand(categoryDao: CategoryDao): UpdateCategoryCommand = UpdateCategoryCommand(categoryDao)

    @Bean
    fun assignPhotosToReceiptCommand(
        receiptDao: ReceiptDao,
        photoDao: PhotoDao
    ) =
        AssignReceiptPhotosCommand(receiptDao, photoDao)

    @Bean
    fun getCategoriesQuery(categoryDao: CategoryDao, receiptConverter: ReceiptConverter): GetCategoriesQuery =
        GetCategoriesQuery(categoryDao, receiptConverter)

    @Bean
    fun getTagsQuery(tagDao: TagDao): GetTagsQuery = GetTagsQuery(tagDao)

    @Bean
    fun getAuthorsQuery(authorDao: AuthorDao): GetAuthorsQuery = GetAuthorsQuery(authorDao)

    @Bean
    fun markReceiptAsFavorite(receiptDao: ReceiptDao) = MarkReceiptAsFavoriteCommand(receiptDao)

    @Bean
    fun receiptFactory(
        getUserByIdQuery: GetUserByIdQuery,
        stepFactory: StepFactory,
        categoryDao: CategoryDao,
        tagDao: TagDao,
        tagFactory: TagFactory,
        authorDao: AuthorDao,
        authorFactory: AuthorFactory,
        photoDao: PhotoDao,
        ingredientGroupFactory: IngredientGroupFactory
    ): ReceiptFactory =
        ReceiptFactory(
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

    @Bean
    fun uploadPhotoCommand(ftpClient: FtpClient, photoDao: PhotoDao, photoFactory: PhotoFactory) =
        UploadPhotoCommand(ftpClient, photoDao, photoFactory)

    @Bean
    fun downloadPhotoCommand(ftpClient: FtpClient, photoDao: PhotoDao) = DownloadPhotoQuery(ftpClient, photoDao)

    @Bean
    fun deletePhotoCommand(ftpClient: FtpClient, photoDao: PhotoDao) = DeletePhotoCommand(ftpClient, photoDao)

    @Bean
    fun addIngredientGroupCommand(
        receiptDao: ReceiptDao,
        ingredientGroupFactory: IngredientGroupFactory,
        ingredientGroupDao: IngredientGroupDao
    ) = AddIngredientGroupCommand(receiptDao, ingredientGroupFactory, ingredientGroupDao)

    @Bean
    fun deleteIngredientGroupCommand(receiptDao: ReceiptDao) = DeleteIngredientGroupCommand(receiptDao)

}