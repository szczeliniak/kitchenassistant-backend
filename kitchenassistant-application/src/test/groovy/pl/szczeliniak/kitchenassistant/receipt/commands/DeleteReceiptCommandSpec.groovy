package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.Author
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotAllowedOperationException
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteReceiptCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    @Subject
    def deleteReceiptCommand = new DeleteReceiptCommand(receiptDao)

    def 'should delete receipt'() {
        given:
        def receipt = receipt(false)
        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt

        when:
        def result = deleteReceiptCommand.execute(1)

        then:
        receipt.deleted
        result == new SuccessResponse(1)
    }

    def 'should throw exception when receipt not found'() {
        given:
        receiptDao.findById(1) >> null

        when:
        deleteReceiptCommand.execute(1)

        then:
        def e = thrown(NotFoundException)
        e.message == "Receipt not found"
    }

    def 'should throw exception when receipt is already marked as deleted'() {
        given:
        def receipt = receipt(true)
        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt

        when:
        deleteReceiptCommand.execute(1)

        then:
        def e = thrown(NotAllowedOperationException)
        e.message == "Receipt is already marked as deleted!"
    }

    private static Receipt receipt(boolean deleted) {
        return new Receipt(1, 2, '', '', new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), '', false, null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), deleted, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
