package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.enums.IngredientUnit
import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.IngredientDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptsResponse
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.StepDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class GetReceiptsQuerySpec extends Specification {

    private ReceiptDao receiptDao = Mock(ReceiptDao)

    @Subject
    private GetReceiptsQuery getReceiptsQuery = new GetReceiptsQuery(receiptDao)

    def "should return receipt"() {
        given:
        def stepCreatedAt = LocalDateTime.now()
        def stepModifiedAt = LocalDateTime.now()
        def ingredientCreatedAt = LocalDateTime.now()
        def ingredientModifiedAt = LocalDateTime.now()
        def receiptCreatedAt = LocalDateTime.now()
        def receiptModifiedAt = LocalDateTime.now()
        def ingredient = ingredient(ingredientCreatedAt, ingredientModifiedAt)
        def step = step(stepCreatedAt, stepModifiedAt)
        def ingredientDto = ingredientDto(ingredientCreatedAt, ingredientModifiedAt)
        def stepDto = stepDto(stepCreatedAt, stepModifiedAt)
        def criteria = new ReceiptCriteria(1)

        receiptDao.findAll(criteria) >> Collections.singletonList(receipt(ingredient, step, receiptCreatedAt, receiptModifiedAt))

        when:
        def result = getReceiptsQuery.execute(criteria)

        then:
        result == new ReceiptsResponse(Collections.singletonList(receiptDto(ingredientDto, stepDto, receiptCreatedAt, receiptModifiedAt)))
    }

    private static Receipt receipt(Ingredient ingredient, Step step, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new Receipt(1,
                2,
                'RECEIPT_NAME',
                'RECEIPT_DESCRIPTION',
                'RECEIPT_AUTHOR',
                'RECEIPT_SOURCE',
                Collections.singletonList(ingredient),
                Collections.singletonList(step),
                false,
                createdAt,
                modifiedAt)
    }

    private static Step step(LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new Step(4, "STEP_TITLE", "STEP_DESCRIPTION", 1, false, createdAt, modifiedAt)
    }

    private static Ingredient ingredient(LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new Ingredient(3, "INGREDIENT_NAME", "INGREDIENT_QUANTITY", IngredientUnit.CUPS, false, createdAt, modifiedAt)
    }

    private static ReceiptDto receiptDto(IngredientDto ingredient, StepDto step, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new ReceiptDto(1, 2, 'RECEIPT_NAME', 'RECEIPT_DESCRIPTION', "RECEIPT_AUTHOR", "RECEIPT_SOURCE", Collections.singletonList(ingredient), Collections.singletonList(step), createdAt, modifiedAt)
    }

    private static IngredientDto ingredientDto(LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new IngredientDto(3, "INGREDIENT_NAME", "INGREDIENT_QUANTITY", IngredientUnit.CUPS, createdAt, modifiedAt)
    }

    private static StepDto stepDto(LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new StepDto(4, "STEP_TITLE", "STEP_DESCRIPTION", 1, createdAt, modifiedAt)
    }

}
