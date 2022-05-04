package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.*
import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class GetReceiptsQuerySpec extends Specification {

    private ReceiptDao receiptDao = Mock(ReceiptDao)

    @Subject
    private GetReceiptsQuery getReceiptsQuery = new GetReceiptsQuery(receiptDao)

    def "should return receipt"() {
        given:
        def criteria = new ReceiptCriteria(1, 2, "NAME", "TAG")
        receiptDao.findAll(criteria, 40, 10) >> Collections.singletonList(receipt())
        receiptDao.count(criteria) >> 413

        when:
        def result = getReceiptsQuery.execute(5, 10, criteria)

        then:
        result == new ReceiptsResponse(Collections.singletonList(receiptDto()),
                new Pagination(5, 10, 42))
    }

    private static Receipt receipt() {
        return new Receipt(1,
                2,
                'RECEIPT_NAME',
                'RECEIPT_DESCRIPTION',
                'RECEIPT_AUTHOR',
                'RECEIPT_SOURCE',
                null,
                Set.of(ingredient()),
                Set.of(step()),
                Set.of(photo(2)),
                Set.of(tag("TAG_NAME")),
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

    private static ReceiptDto receiptDto() {
        return new ReceiptDto(1, 'RECEIPT_NAME', 'RECEIPT_DESCRIPTION', "RECEIPT_AUTHOR", "RECEIPT_SOURCE",
                null, Set.of(ingredientDto()), Set.of(stepDto()), Set.of(photoDto(2)), Set.of("TAG_NAME"))
    }

    private static IngredientDto ingredientDto() {
        return new IngredientDto(3, "INGREDIENT_NAME", "INGREDIENT_QUANTITY")
    }

    private static StepDto stepDto() {
        return new StepDto(4, "STEP_NAME", "STEP_DESCRIPTION", 1)
    }

    private static Photo photo(Integer fileId) {
        return new Photo(99, fileId, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Tag tag(String name) {
        return new Tag(98, name, 2, LocalDateTime.now(), LocalDateTime.now())
    }

    private static PhotoDto photoDto(Integer fileId) {
        return new PhotoDto(99, fileId)
    }

}
