package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateReceiptDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class UpdateReceiptCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    @Subject
    def updateReceiptCommand = new UpdateReceiptCommand(receiptDao)

    def 'should update receipt'() {
        given:
        def receipt = receipt()
        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt

        when:
        def result = updateReceiptCommand.execute(1, updateReceiptDto())

        then:
        receipt.name == "NAME"
        receipt.description == "DESC"
        receipt.author == "AUTHOR"
        receipt.source == "SOURCE"
        receipt.userId == 2
        result == new SuccessResponse(1)
    }

    private static UpdateReceiptDto updateReceiptDto() {
        return new UpdateReceiptDto(2, "NAME", "DESC", "AUTHOR", "SOURCE")
    }

    private static Receipt receipt() {
        return new Receipt(1, 1, "", "", "", "", Collections.emptyList(),
                Collections.emptyList(), false, LocalDateTime.now(), LocalDateTime.now())
    }
}
