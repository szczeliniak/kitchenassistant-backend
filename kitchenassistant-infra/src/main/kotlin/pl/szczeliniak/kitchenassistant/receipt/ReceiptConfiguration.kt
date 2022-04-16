package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.receipt.commands.*
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.*
import pl.szczeliniak.kitchenassistant.receipt.queries.GetCategoriesQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptsQuery
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
    fun updateReceiptCommand(receiptDao: ReceiptDao, categoryDao: CategoryDao): UpdateReceiptCommand =
        UpdateReceiptCommand(receiptDao, categoryDao)

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
    fun stepFactory(): StepFactory = StepFactory()

    @Bean
    fun ingredientFactory(): IngredientFactory = IngredientFactory()

    @Bean
    fun categoryFactory(): CategoryFactory = CategoryFactory()

    @Bean
    fun photoFactory(): PhotoFactory = PhotoFactory()

    @Bean
    fun addCategoryCommand(categoryDao: CategoryDao, categoryFactory: CategoryFactory): AddCategoryCommand =
        AddCategoryCommand(categoryDao, categoryFactory)

    @Bean
    fun deleteCategoryCommand(categoryDao: CategoryDao): DeleteCategoryCommand = DeleteCategoryCommand(categoryDao)

    @Bean
    fun updateCategoryCommand(categoryDao: CategoryDao): UpdateCategoryCommand = UpdateCategoryCommand(categoryDao)

    @Bean
    fun addReceiptPhotosCommand(receiptDao: ReceiptDao, photoDao: PhotoDao, photoFactory: PhotoFactory) =
        AddReceiptPhotosCommand(receiptDao, photoDao, photoFactory)

    @Bean
    fun deleteReceiptPhotoCommand(receiptDao: ReceiptDao) = DeleteReceiptPhotoCommand(receiptDao)


    @Bean
    fun getCategoriesQuery(categoryDao: CategoryDao): GetCategoriesQuery = GetCategoriesQuery(categoryDao)

    @Bean
    fun receiptFactory(
        getUserByIdQuery: GetUserByIdQuery,
        stepFactory: StepFactory,
        ingredientFactory: IngredientFactory,
        photoFactory: PhotoFactory,
        categoryDao: CategoryDao
    ): ReceiptFactory = ReceiptFactory(getUserByIdQuery, ingredientFactory, stepFactory, photoFactory, categoryDao)

}