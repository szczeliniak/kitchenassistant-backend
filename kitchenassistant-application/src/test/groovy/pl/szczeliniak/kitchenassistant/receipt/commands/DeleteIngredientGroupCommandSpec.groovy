package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.IngredientGroup
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteIngredientGroupCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    @Subject
    def deleteIngredientGroupCommand = new DeleteIngredientGroupCommand(receiptDao)

    def 'should delete ingredient group'() {
        given:
        def ingredientGroup = ingredientGroup()
        def receipt = receipt(ingredientGroup)
        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt

        when:
        def result = deleteIngredientGroupCommand.execute(1, 2)

        then:
        ingredientGroup.deleted
        result == new SuccessResponse(2)
    }

    private static Receipt receipt(IngredientGroup ingredientGroup) {
        return new Receipt(1, 1, "", "", null, "", false, null, Collections.singleton(ingredientGroup), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static IngredientGroup ingredientGroup() {
        return new IngredientGroup(2, "NAME", Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
