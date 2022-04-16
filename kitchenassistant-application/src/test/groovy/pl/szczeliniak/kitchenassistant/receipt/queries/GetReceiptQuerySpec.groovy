package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.IngredientDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptResponse
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.StepDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class GetReceiptQuerySpec extends Specification {

    private ReceiptDao receiptDao = Mock(ReceiptDao)

    @Subject
    private GetReceiptQuery getReceiptQuery = new GetReceiptQuery(receiptDao)

    def "should return receipt"() {
        given:
        def ingredient = ingredient()
        def step = step()
        def ingredientDto = ingredientDto()
        def stepDto = stepDto()
        def photo = photo()

        receiptDao.findById(1) >> receipt(ingredient, step, photo)

        when:
        def result = getReceiptQuery.execute(1)

        then:
        result == new ReceiptResponse(receiptDto(ingredientDto, stepDto))
    }

    def "should throw exception receipt not found"() {
        given:
        receiptDao.findById(1) >> null

        when:
        getReceiptQuery.execute(1)

        then:
        def e = thrown(NotFoundException)
        e.message == "Receipt not found"
    }

    private static Receipt receipt(Ingredient ingredient, Step step, Photo photo) {
        return new Receipt(1,
                2,
                'RECEIPT_NAME',
                'RECEIPT_DESCRIPTION',
                'RECEIPT_AUTHOR',
                'RECEIPT_SOURCE',
                null,
                Collections.singletonList(ingredient),
                Collections.singletonList(step),
                Collections.singletonList(photo),
                false,
                LocalDateTime.now(),
                LocalDateTime.now())
    }

    private static Step step() {
        return new Step(4, "STEP_NAME", "STEP_DESCRIPTION", 1, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Ingredient ingredient() {
        return new Ingredient(3, "INGREDIENT_NAME", "INGREDIENT_QUANTITY", false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static ReceiptDto receiptDto(IngredientDto ingredient, StepDto step) {
        return new ReceiptDto(1, 'RECEIPT_NAME', 'RECEIPT_DESCRIPTION', "RECEIPT_AUTHOR", "RECEIPT_SOURCE", null, Collections.singletonList(ingredient), Collections.singletonList(step), Collections.singletonList("PHOTO_NAME"))
    }

    private static IngredientDto ingredientDto() {
        return new IngredientDto(3, "INGREDIENT_NAME", "INGREDIENT_QUANTITY")
    }

    private static StepDto stepDto() {
        return new StepDto(4, "STEP_NAME", "STEP_DESCRIPTION", 1)
    }

    private static Photo photo() {
        return new Photo(99, "PHOTO_NAME", false, LocalDateTime.now(), LocalDateTime.now())
    }

}
