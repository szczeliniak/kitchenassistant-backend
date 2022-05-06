package pl.szczeliniak.kitchenassistant.receipt.queries


import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptCriteria
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptsResponse
import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class GetReceiptsQuerySpec extends Specification {

    private ReceiptDao receiptDao = Mock(ReceiptDao)
    private receiptConverter = Mock(ReceiptConverter)

    @Subject
    private GetReceiptsQuery getReceiptsQuery = new GetReceiptsQuery(receiptDao, receiptConverter)

    def "should return receipt"() {
        given:
        def criteria = new ReceiptCriteria(1, 1, '', '')
        def receipt = receipt()
        def receiptDto = receiptDto()
        receiptDao.findAll(criteria, 40, 10) >> Collections.singletonList(receipt)
        receiptDao.count(criteria) >> 413
        receiptConverter.map(receipt) >> receiptDto

        when:
        def result = getReceiptsQuery.execute(5, 10, criteria)

        then:
        result == new ReceiptsResponse(Collections.singletonList(receiptDto),
                new Pagination(5, 10, 42))
    }

    private static Receipt receipt() {
        return new Receipt(1,
                0,
                '',
                '',
                '',
                '',
                null,
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet(),
                false,
                LocalDateTime.now(),
                LocalDateTime.now())
    }

    private static ReceiptDto receiptDto() {
        return new ReceiptDto(1, '', '', "", "",
                null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet())
    }

}
