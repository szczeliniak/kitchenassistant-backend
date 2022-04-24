package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.*
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class GetReceiptQuerySpec extends Specification {

    private ReceiptDao receiptDao = Mock(ReceiptDao)

    @Subject
    private GetReceiptQuery getReceiptQuery = new GetReceiptQuery(receiptDao)

    def "should return receipt"() {
        given:
        receiptDao.findById(1) >> receipt()

        when:
        def result = getReceiptQuery.execute(1)

        then:
        result == new ReceiptResponse(receiptDto())
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

    private static Receipt receipt() {
        return new Receipt(1,
                4,
                'RECEIPT_NAME',
                'RECEIPT_DESCRIPTION',
                'RECEIPT_AUTHOR',
                'RECEIPT_SOURCE',
                null,
                Set.of(ingredient()),
                Set.of(step()),
                Set.of(photo(1)),
                Set.of(tag("TAG_NAME")),
                false,
                LocalDateTime.now(),
                LocalDateTime.now())
    }

    private static Step step() {
        return new Step(4, "STEP_NAME", "STEP_DESCRIPTION", 1, Set.of(photo(2)), false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Ingredient ingredient() {
        return new Ingredient(3, "INGREDIENT_NAME", "INGREDIENT_QUANTITY", false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static ReceiptDto receiptDto() {
        return new ReceiptDto(1, 'RECEIPT_NAME', 'RECEIPT_DESCRIPTION', "RECEIPT_AUTHOR", "RECEIPT_SOURCE", null, Set.of(ingredientDto()), Set.of(stepDto()), Set.of(photoDto(1)), Set.of("TAG_NAME"))
    }

    private static IngredientDto ingredientDto() {
        return new IngredientDto(3, "INGREDIENT_NAME", "INGREDIENT_QUANTITY")
    }

    private static StepDto stepDto() {
        return new StepDto(4, "STEP_NAME", "STEP_DESCRIPTION", 1, Collections.singletonList(photoDto(2)))
    }

    private static Photo photo(Integer fileId) {
        return new Photo(99, fileId, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Tag tag(String name) {
        return new Tag(98, name, 4, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static PhotoDto photoDto(Integer fileId) {
        return new PhotoDto(99, fileId)
    }

}
