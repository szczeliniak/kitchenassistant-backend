package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.Author
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class GetReceiptQuerySpec extends Specification {

    private ReceiptDao receiptDao = Mock(ReceiptDao)
    private receiptConverter = Mock(ReceiptConverter)

    @Subject
    private GetReceiptQuery getReceiptQuery = new GetReceiptQuery(receiptDao, receiptConverter)

    def "should return receipt"() {
        given:
        def receipt = receipt()
        def receiptDto = receiptDto()
        receiptDao.findById(1) >> receipt
        receiptConverter.map(receipt) >> receiptDto

        when:
        def result = getReceiptQuery.execute(1)

        then:
        result == new ReceiptResponse(receiptDto)
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
                0,
                '',
                '',
                new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()),
                '',
                false,
                null,
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet(),
                false,
                ZonedDateTime.now(),
                ZonedDateTime.now())
    }

    private static ReceiptDto receiptDto() {
        return new ReceiptDto(1, '', '', "", "", null, null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet())
    }

}
