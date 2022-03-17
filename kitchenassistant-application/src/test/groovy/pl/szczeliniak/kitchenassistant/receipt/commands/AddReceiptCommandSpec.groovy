package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.ReceiptFactory
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class AddReceiptCommandSpec extends Specification {
    def receiptDao = Mock(ReceiptDao)
    def receiptFactory = Mock(ReceiptFactory)
    @Subject
    def addReceiptCommand = new AddReceiptCommand(receiptDao, receiptFactory)

    def 'should save receipt'() {
        given:
        def receipt = receipt()
        def dto = newReceiptDto()
        receiptFactory.create(dto) >> receipt
        receiptDao.save(receipt) >> receipt

        when:
        def result = addReceiptCommand.execute(dto)

        then:
        result == new SuccessResponse(1)
    }

    private static NewReceiptDto newReceiptDto() {
        return new NewReceiptDto(1, "", "", "", "", Collections.emptyList(), Collections.emptyList())
    }

    private static Receipt receipt() {
        return new Receipt(1, 2, "", "", "", "", Collections.emptyList(), Collections.emptyList(), false, LocalDateTime.now(), LocalDateTime.now())
    }

}
