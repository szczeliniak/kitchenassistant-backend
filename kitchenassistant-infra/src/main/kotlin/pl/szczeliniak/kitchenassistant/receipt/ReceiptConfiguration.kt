package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.receipt.commands.AddNewReceipt
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.IngredientFactory
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.ReceiptFactory
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.StepFactory
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceipt
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceipts
import pl.szczeliniak.kitchenassistant.user.UserDao

@Configuration
class ReceiptConfiguration {

    @Bean
    fun getReceipt(receiptDao: ReceiptDao): GetReceipt = GetReceipt(receiptDao)

    @Bean
    fun getReceipts(receiptDao: ReceiptDao): GetReceipts = GetReceipts(receiptDao)

    @Bean
    fun addNewReceipt(receiptDao: ReceiptDao, receiptFactory: ReceiptFactory): AddNewReceipt =
        AddNewReceipt(receiptDao, receiptFactory)

    @Bean
    fun stepFactory(): StepFactory = StepFactory()

    @Bean
    fun ingredientFactory(): IngredientFactory = IngredientFactory()

    @Bean
    fun receiptFactory(
        userDao: UserDao,
        stepFactory: StepFactory,
        ingredientFactory: IngredientFactory
    ): ReceiptFactory = ReceiptFactory(userDao, ingredientFactory, stepFactory)

}