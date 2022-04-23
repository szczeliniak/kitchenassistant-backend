package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotAllowedOperationException
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

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
        return new Receipt(1, 2, '', '', '', '', null, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), deleted, LocalDateTime.now(), LocalDateTime.now())
    }

}
