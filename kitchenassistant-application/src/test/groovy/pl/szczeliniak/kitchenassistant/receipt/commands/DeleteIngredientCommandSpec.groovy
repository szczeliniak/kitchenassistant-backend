package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteIngredientCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def ingredientDao = Mock(IngredientDao)

    @Subject
    def deleteIngredientCommand = new DeleteIngredientCommand(receiptDao, ingredientDao)

    def 'should delete ingredient'() {
        given:
        def ingredient = ingredient(false)
        def receipt = receipt(Set.of(ingredient))
        receiptDao.findById(1) >> receipt
        ingredientDao.save(ingredient) >> ingredient

        when:
        def result = deleteIngredientCommand.execute(1, 3)

        then:
        ingredient.deleted
        result == new SuccessResponse(1)
    }

    def 'should throw exception when ingredient not found'() {
        given:
        def receipt = receipt(Collections.emptySet())
        receiptDao.findById(1) >> receipt

        when:
        deleteIngredientCommand.execute(1, 3)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Ingredient not found"
    }

    def 'should throw exception when ingredient is already marked as deleted'() {
        given:
        def ingredient = ingredient(true)
        def receipt = receipt(Set.of(ingredient))
        receiptDao.findById(1) >> receipt

        when:
        deleteIngredientCommand.execute(1, 3)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Ingredient is already marked as deleted!"
    }

    private static Receipt receipt(Set<Ingredient> ingredients) {
        return new Receipt(1, 2, '', '', new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), '', false, null, ingredients, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Ingredient ingredient(boolean deleted) {
        return new Ingredient(3, '', '', deleted, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
