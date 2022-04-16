package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.IngredientDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptsResponse
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.StepDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.Pagination
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class GetReceiptsQuerySpec extends Specification {

    private ReceiptDao receiptDao = Mock(ReceiptDao)

    @Subject
    private GetReceiptsQuery getReceiptsQuery = new GetReceiptsQuery(receiptDao)

    def "should return receipt"() {
        given:
        def ingredient = ingredient()
        def step = step()
        def ingredientDto = ingredientDto()
        def stepDto = stepDto()
        def photo = photo("PHOTO_NAME")
        def criteria = new ReceiptCriteria(1, 2, "NAME")

        receiptDao.findAll(criteria, 40, 10) >> Collections.singletonList(receipt(ingredient, step, photo))
        receiptDao.count(criteria) >> 413

        when:
        def result = getReceiptsQuery.execute(5, 10, criteria)

        then:
        result == new ReceiptsResponse(Collections.singletonList(receiptDto(ingredientDto, stepDto)),
                new Pagination(5, 10, 42))
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
        return new Step(4, "STEP_NAME", "STEP_DESCRIPTION", 1, Collections.singletonList(photo("STEP_PHOTO_NAME")), false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Ingredient ingredient() {
        return new Ingredient(3, "INGREDIENT_NAME", "INGREDIENT_QUANTITY", false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static ReceiptDto receiptDto(IngredientDto ingredient, StepDto step) {
        return new ReceiptDto(1, 'RECEIPT_NAME', 'RECEIPT_DESCRIPTION', "RECEIPT_AUTHOR", "RECEIPT_SOURCE",
                null, Collections.singletonList(ingredient), Collections.singletonList(step), Collections.singletonList("PHOTO_NAME"))
    }

    private static IngredientDto ingredientDto() {
        return new IngredientDto(3, "INGREDIENT_NAME", "INGREDIENT_QUANTITY")
    }

    private static StepDto stepDto() {
        return new StepDto(4, "STEP_NAME", "STEP_DESCRIPTION", 1, Collections.singletonList("STEP_PHOTO_NAME"))
    }

    private static Photo photo(String name) {
        return new Photo(99, name, false, LocalDateTime.now(), LocalDateTime.now())
    }

}
