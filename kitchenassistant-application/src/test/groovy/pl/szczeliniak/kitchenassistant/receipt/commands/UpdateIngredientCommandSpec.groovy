package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateIngredientDto
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class UpdateIngredientCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def ingredientDao = Mock(IngredientDao)

    @Subject
    def updateIngredientCommand = new UpdateIngredientCommand(receiptDao, ingredientDao)

    def 'should update ingredient'() {
        given:
        def ingredient = ingredient()
        receiptDao.findById(1) >> receipt(Set.of(ingredient))
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

    private static Receipt receipt(Set<Ingredient> ingredients) {
        return new Receipt(1, 1, "", "", new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), "", false, null, ingredients,
                Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Ingredient ingredient() {
        return new Ingredient(2, "", "", false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}