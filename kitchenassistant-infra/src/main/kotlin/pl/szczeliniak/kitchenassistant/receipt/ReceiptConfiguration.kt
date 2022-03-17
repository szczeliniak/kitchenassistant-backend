package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.receipt.commands.*
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.IngredientFactory
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.ReceiptFactory
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.StepFactory
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
    fun addIngredientCommand(
        receiptDao: ReceiptDao,
        ingredientDao: IngredientDao,
        ingredientFactory: IngredientFactory
    ): AddIngredientCommand =
        AddIngredientCommand(receiptDao, ingredientDao, ingredientFactory)

    @Bean
    fun addStepCommand(receiptDao: ReceiptDao, stepDao: StepDao, stepFactory: StepFactory): AddStepCommand =
        AddStepCommand(receiptDao, stepDao, stepFactory)

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
    fun receiptFactory(
        getUserByIdQuery: GetUserByIdQuery,
        stepFactory: StepFactory,
        ingredientFactory: IngredientFactory
    ): ReceiptFactory = ReceiptFactory(getUserByIdQuery, ingredientFactory, stepFactory)

}