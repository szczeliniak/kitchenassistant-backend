package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.receipt.commands.AddNewReceiptCommand
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.IngredientFactory
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.ReceiptFactory
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.StepFactory
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptsQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUserQuery

@Configuration
class ReceiptConfiguration {

    @Bean
    fun getReceiptQuery(receiptDao: ReceiptDao): GetReceiptQuery = GetReceiptQuery(receiptDao)

    @Bean
    fun getReceiptsQuery(receiptDao: ReceiptDao): GetReceiptsQuery = GetReceiptsQuery(receiptDao)

    @Bean
    fun addNewReceiptCommand(receiptDao: ReceiptDao, receiptFactory: ReceiptFactory): AddNewReceiptCommand =
        AddNewReceiptCommand(receiptDao, receiptFactory)

    @Bean
    fun stepFactory(): StepFactory = StepFactory()

    @Bean
    fun ingredientFactory(): IngredientFactory = IngredientFactory()

    @Bean
    fun receiptFactory(
        getUserQuery: GetUserQuery,
        stepFactory: StepFactory,
        ingredientFactory: IngredientFactory
    ): ReceiptFactory = ReceiptFactory(getUserQuery, ingredientFactory, stepFactory)

}