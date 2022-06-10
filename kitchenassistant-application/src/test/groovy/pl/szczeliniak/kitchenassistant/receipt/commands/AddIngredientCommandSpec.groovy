package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.IngredientFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

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
        def result = addIngredientCommand.execute(1, 2, dto)

        then:
        result == new SuccessResponse(3)
        receipt.ingredientGroups[0].ingredients == Set.of(ingredient)
    }

    private static NewIngredientDto newIngredientDto() {
        return new NewIngredientDto("", "")
    }

    private static Ingredient ingredient() {
        return new Ingredient(3, "", "", false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Receipt receipt() {
        return new Receipt(1, 0, "", "", new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), "", false, null, Collections.singleton(ingredientGroup()), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static IngredientGroup ingredientGroup() {
        return new IngredientGroup(2, "GROUP_NAME", new HashSet<Ingredient>(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
