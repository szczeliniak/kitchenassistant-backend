package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.Ingredient
import pl.szczeliniak.kitchenassistant.receipt.IngredientDao
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotAllowedOperationException
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

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
        def e = thrown(NotFoundException)
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
        def e = thrown(NotAllowedOperationException)
        e.message == "Ingredient is already marked as deleted!"
    }

    private static Receipt receipt(Set<Ingredient> ingredients) {
        return new Receipt(1, 2, '', '', '', '', null, ingredients, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Ingredient ingredient(boolean deleted) {
        return new Ingredient(3, '', '', deleted, LocalDateTime.now(), LocalDateTime.now())
    }

}
