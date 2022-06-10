package pl.szczeliniak.kitchenassistant.receipt.commands


import pl.szczeliniak.kitchenassistant.receipt.IngredientGroup
import pl.szczeliniak.kitchenassistant.receipt.IngredientGroupDao
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientGroupDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.IngredientGroupFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class AddIngredientGroupCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def ingredientGroupFactory = Mock(IngredientGroupFactory)
    def ingredientGroupDao = Mock(IngredientGroupDao)

    @Subject
    def addIngredientGroupCommand = new AddIngredientGroupCommand(receiptDao, ingredientGroupFactory, ingredientGroupDao)

    def 'should add ingredient group'() {
        given:
        def newIngredientGroupDto = newIngredientGroupDto()
        def ingredientGroup = ingredientGroup()
        def receipt = receipt()
        receiptDao.findById(1) >> receipt
        ingredientGroupFactory.create(newIngredientGroupDto) >> ingredientGroup
        ingredientGroupDao.save(ingredientGroup) >> ingredientGroup
        receiptDao.save(receipt) >> receipt
        when:
        def result = addIngredientGroupCommand.execute(1, newIngredientGroupDto)
        then:
        result == new SuccessResponse(2)
        receipt.ingredientGroups.contains(ingredientGroup)
    }

    private static NewIngredientGroupDto newIngredientGroupDto() {
        return new NewIngredientGroupDto("GROUP", Collections.emptySet())
    }

    private static IngredientGroup ingredientGroup() {
        return new IngredientGroup(2, "NAME", Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Receipt receipt() {
        return new Receipt(1, 1, "", "", null, "", false, null, new HashSet<IngredientGroup>(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }
}
