package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.receipt.Ingredient
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.Step
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientDto
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class ReceiptFactorySpec extends Specification {

    def getUserQuery = Mock(GetUserByIdQuery)
    def ingredientFactory = Mock(IngredientFactory)
    def stepFactory = Mock(StepFactory)

    @Subject
    def receiptFactory = new ReceiptFactory(getUserQuery, ingredientFactory, stepFactory)

    def 'should create receipt'() {
        given:
        def newIngredientDto = newIngredientDto()
        def newStepDto = newStepDto()
        getUserQuery.execute(1) >> userResponse()
        ingredientFactory.create(newIngredientDto) >> ingredient()
        stepFactory.create(newStepDto) >> step()

        when:
        def result = receiptFactory.create(newReceiptDto(newIngredientDto, newStepDto))

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt_", "modifiedAt_", "ingredients_.createdAt_",
                        "ingredients_.modifiedAt_", "steps_.createdAt_", "steps_.modifiedAt_")
                .isEqualTo(receipt())
    }

    private static NewReceiptDto newReceiptDto(NewIngredientDto newIngredientDto, NewStepDto newStepDto) {
        return new NewReceiptDto(2, "RECEIPT_NAME", "RECEIPT_DESCRIPTION", "RECEIPT_AUTHOR",
                "RECEIPT_SOURCE", Collections.singletonList(newIngredientDto), Collections.singletonList(newStepDto))
    }

    private static NewIngredientDto newIngredientDto() {
        return new NewIngredientDto("", "")
    }

    private static NewStepDto newStepDto() {
        return new NewStepDto("", "", 0)
    }

    private static UserResponse userResponse() {
        return new UserResponse(new UserDto(1, "", ""))
    }

    private static Receipt receipt() {
        return new Receipt(0, 2, "RECEIPT_NAME", "RECEIPT_DESCRIPTION", "RECEIPT_AUTHOR",
                "RECEIPT_SOURCE", Collections.singletonList(ingredient()), Collections.singletonList(step()),
                false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Ingredient ingredient() {
        return new Ingredient(3, "INGREDIENT_NAME", "INGREDIENT_QUANTITY", false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Step step() {
        return new Step(4, "STEP_NAME", "STEP_DESCRIPTION", 1, false, LocalDateTime.now(), LocalDateTime.now())
    }

}
