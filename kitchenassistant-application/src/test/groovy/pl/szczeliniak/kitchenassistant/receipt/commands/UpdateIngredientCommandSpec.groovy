package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.Ingredient
import pl.szczeliniak.kitchenassistant.receipt.IngredientDao
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateIngredientDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class UpdateIngredientCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def ingredientDao = Mock(IngredientDao)

    @Subject
    def updateIngredientCommand = new UpdateIngredientCommand(receiptDao, ingredientDao)

    def 'should update ingredient'() {
        given:
        def ingredient = ingredient()
        receiptDao.findById(1) >> receipt(Collections.singletonList(ingredient))
        ingredientDao.save(ingredient) >> ingredient

        when:
        def result = updateIngredientCommand.execute(1, 2, updateIngredientDto())

        then:
        ingredient.name == "NAME"
        ingredient.quantity == "QUANTITY"
        result == new SuccessResponse(2)
    }

    private static UpdateIngredientDto updateIngredientDto() {
        return new UpdateIngredientDto("NAME", "QUANTITY")
    }

    private static Receipt receipt(List<Ingredient> ingredients) {
        return new Receipt(1, 1, "", "", "", "", null, ingredients,
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Ingredient ingredient() {
        return new Ingredient(2, "", "", false, LocalDateTime.now(), LocalDateTime.now())
    }

}