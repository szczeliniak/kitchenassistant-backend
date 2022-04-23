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
                Collections.singletonList(ingredient()),
                Collections.singletonList(step()),
                Collections.singletonList(photo("PHOTO_NAME")),
                Collections.singletonList(tag("TAG_NAME")),
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

    private static ReceiptDto receiptDto() {
        return new ReceiptDto(1, 'RECEIPT_NAME', 'RECEIPT_DESCRIPTION', "RECEIPT_AUTHOR", "RECEIPT_SOURCE", null, Collections.singletonList(ingredientDto()), Collections.singletonList(stepDto()), Collections.singletonList(fileDto("PHOTO_NAME")), Collections.singletonList("TAG_NAME"))
    }

    private static IngredientDto ingredientDto() {
        return new IngredientDto(3, "INGREDIENT_NAME", "INGREDIENT_QUANTITY")
    }

    private static StepDto stepDto() {
        return new StepDto(4, "STEP_NAME", "STEP_DESCRIPTION", 1, Collections.singletonList(fileDto("STEP_PHOTO_NAME")))
    }

    private static File photo(String name) {
        return new File(99, name, 4, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Tag tag(String name) {
        return new Tag(98, name, 4, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static FileDto fileDto(String name) {
        return new FileDto(99, name)
    }

}
