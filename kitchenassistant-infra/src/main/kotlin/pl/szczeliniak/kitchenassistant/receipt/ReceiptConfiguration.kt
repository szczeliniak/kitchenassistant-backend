package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.file.queries.CheckIfFileExistsQuery
import pl.szczeliniak.kitchenassistant.receipt.commands.*
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.*
import pl.szczeliniak.kitchenassistant.receipt.queries.GetCategoriesQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptsQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.GetTagsQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery

@Configuration
class ReceiptConfiguration {

    @Bean
    fun getReceiptQuery(receiptDao: ReceiptDao): GetReceiptQuery = GetReceiptQuery(receiptDao)

    @Bean
    fun getReceiptsQuery(receiptDao: ReceiptDao): GetReceiptsQuery = GetReceiptsQuery(receiptDao)

    @Bean
    fun addReceiptCommand(receiptDao: ReceiptDao, receiptFactory: ReceiptFactory): AddReceiptCommand =
        AddReceiptCommand(receiptDao, receiptFactory)

    @Bean
    fun updateReceiptCommand(
        receiptDao: ReceiptDao,
        categoryDao: CategoryDao,
        tagDao: TagDao,
        tagFactory: TagFactory,
        photoFactory: PhotoFactory,
        photoDao: PhotoDao
    ): UpdateReceiptCommand =
        UpdateReceiptCommand(receiptDao, categoryDao, tagDao, tagFactory, photoFactory, photoDao)

    @Bean
    fun addIngredientCommand(
        receiptDao: ReceiptDao,
        ingredientDao: IngredientDao,
        ingredientFactory: IngredientFactory
    ): AddIngredientCommand =
        AddIngredientCommand(receiptDao, ingredientDao, ingredientFactory)

    @Bean
    fun updateIngredientCommand(receiptDao: ReceiptDao, ingredientDao: IngredientDao): UpdateIngredientCommand =
        UpdateIngredientCommand(receiptDao, ingredientDao)

    @Bean
    fun addStepCommand(receiptDao: ReceiptDao, stepDao: StepDao, stepFactory: StepFactory): AddStepCommand =
        AddStepCommand(receiptDao, stepDao, stepFactory)

    @Bean
    fun updateStepCommand(receiptDao: ReceiptDao, stepDao: StepDao): UpdateStepCommand =
        UpdateStepCommand(receiptDao, stepDao)

    @Bean
    fun deleteReceiptCommand(receiptDao: ReceiptDao): DeleteReceiptCommand = DeleteReceiptCommand(receiptDao)

    @Bean
    fun deleteStepCommand(receiptDao: ReceiptDao): DeleteStepCommand = DeleteStepCommand(receiptDao)

    @Bean
    fun deleteIngredientCommand(receiptDao: ReceiptDao): DeleteIngredientCommand = DeleteIngredientCommand(receiptDao)

    @Bean
    fun stepFactory(photoFactory: PhotoFactory, checkIfFileExistsQuery: CheckIfFileExistsQuery): StepFactory =
        StepFactory(photoFactory, checkIfFileExistsQuery)

    @Bean
    fun ingredientFactory(): IngredientFactory = IngredientFactory()

    @Bean
    fun categoryFactory(): CategoryFactory = CategoryFactory()

    @Bean
    fun photoFactory(): PhotoFactory = PhotoFactory()

    @Bean
    fun tagFactory(): TagFactory = TagFactory()

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
        checkIfFileExistsQuery: CheckIfFileExistsQuery,
        photoFactory: PhotoFactory,
        photoDao: PhotoDao
    ) =
        AssignReceiptPhotosCommand(receiptDao, checkIfFileExistsQuery, photoFactory, photoDao)

    @Bean
    fun divestPhotoFromReceiptCommand(receiptDao: ReceiptDao) = DivestReceiptPhotoCommand(receiptDao)

    @Bean
    fun getCategoriesQuery(categoryDao: CategoryDao): GetCategoriesQuery = GetCategoriesQuery(categoryDao)

    @Bean
    fun getTagsQuery(tagsDao: TagDao): GetTagsQuery = GetTagsQuery(tagsDao)

    @Bean
    fun receiptFactory(
        getUserByIdQuery: GetUserByIdQuery,
        ingredientFactory: IngredientFactory,
        stepFactory: StepFactory,
        categoryDao: CategoryDao,
        photoFactory: PhotoFactory,
        tagDao: TagDao,
        tagFactory: TagFactory,
        checkIfFileExistsQuery: CheckIfFileExistsQuery
    ): ReceiptFactory =
        ReceiptFactory(
            getUserByIdQuery,
            ingredientFactory,
            stepFactory,
            categoryDao,
            photoFactory,
            tagDao,
            tagFactory,
            checkIfFileExistsQuery
        )

    @Bean
    fun assignPhotosToReceiptStepCommand(
        receiptDao: ReceiptDao,
        stepDao: StepDao,
        checkIfFileExistsQuery: CheckIfFileExistsQuery,
        photoFactory: PhotoFactory,
        photoDao: PhotoDao
    ): AssignStepPhotosCommand =
        AssignStepPhotosCommand(receiptDao, stepDao, checkIfFileExistsQuery, photoFactory, photoDao)

    @Bean
    fun divestPhotoFromReceiptStepCommand(receiptDao: ReceiptDao, stepDao: StepDao) =
        DivestStepPhotoCommand(receiptDao, stepDao)

}