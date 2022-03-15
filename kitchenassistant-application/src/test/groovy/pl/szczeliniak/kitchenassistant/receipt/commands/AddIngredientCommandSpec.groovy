package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.enums.IngredientUnit
import pl.szczeliniak.kitchenassistant.receipt.Ingredient
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.IngredientFactory
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class AddIngredientCommandSpec extends Specification {
    def receiptDao = Mock(ReceiptDao)
    def ingredientFactory = Mock(IngredientFactory)
    @Subject
    def addIngredientCommand = new AddIngredientCommand(receiptDao, ingredientFactory)

    def 'should save ingredient'() {
        given:
        def receipt = receipt()
        def dto = newIngredientDto()
        def ingredient = ingredient()
        receiptDao.findById(1) >> receipt
        ingredientFactory.create(dto) >> ingredient
        receiptDao.save(receipt) >> receipt

        when:
        def result = addIngredientCommand.execute(1, dto)

        then:
        result == new SuccessResponse()
        receipt.ingredients == Collections.singletonList(ingredient)
    }

    private static NewIngredientDto newIngredientDto() {
        return new NewIngredientDto("", "", IngredientUnit.CUPS)
    }

    private static Ingredient ingredient() {
        return new Ingredient(0, "", "", IngredientUnit.CUPS, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Receipt receipt() {
        return new Receipt(0, 0, "", "", "", "", new ArrayList<Ingredient>(), Collections.emptyList(), false, LocalDateTime.now(), LocalDateTime.now())
    }

}
