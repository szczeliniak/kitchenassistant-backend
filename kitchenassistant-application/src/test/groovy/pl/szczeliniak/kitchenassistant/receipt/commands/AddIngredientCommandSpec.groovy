package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.Ingredient
import pl.szczeliniak.kitchenassistant.receipt.IngredientDao
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.IngredientFactory
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class AddIngredientCommandSpec extends Specification {
    def receiptDao = Mock(ReceiptDao)
    def ingredientDao = Mock(IngredientDao)
    def ingredientFactory = Mock(IngredientFactory)
    @Subject
    def addIngredientCommand = new AddIngredientCommand(receiptDao, ingredientDao, ingredientFactory)

    def 'should save ingredient'() {
        given:
        def receipt = receipt()
        def dto = newIngredientDto()
        def ingredient = ingredient()
        receiptDao.findById(1) >> receipt
        ingredientFactory.create(dto) >> ingredient
        ingredientDao.save(ingredient) >> ingredient
        receiptDao.save(receipt) >> receipt

        when:
        def result = addIngredientCommand.execute(1, dto)

        then:
        result == new SuccessResponse(2)
        receipt.ingredients == Collections.singletonList(ingredient)
    }

    private static NewIngredientDto newIngredientDto() {
        return new NewIngredientDto("", "")
    }

    private static Ingredient ingredient() {
        return new Ingredient(2, "", "", false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Receipt receipt() {
        return new Receipt(1, 0, "", "", "", "", null, new ArrayList<Ingredient>(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), false, LocalDateTime.now(), LocalDateTime.now())
    }

}
