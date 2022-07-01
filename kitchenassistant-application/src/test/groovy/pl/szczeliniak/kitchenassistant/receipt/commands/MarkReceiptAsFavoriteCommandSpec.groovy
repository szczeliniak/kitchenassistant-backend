package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.Author
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class MarkReceiptAsFavoriteCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)

    @Subject
    def markReceiptAsFavoriteCommand = new MarkReceiptAsFavoriteCommand(receiptDao)

    def 'should mark receipt as favorite'() {
        given:
        def receipt = receipt()
        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt

        when:
        def result = markReceiptAsFavoriteCommand.execute(1, true)

        then:
        receipt.favorite
        result == new SuccessResponse(1)
    }

    private static Receipt receipt() {
        return new Receipt(1, '', 2, '', new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), '', false, null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
