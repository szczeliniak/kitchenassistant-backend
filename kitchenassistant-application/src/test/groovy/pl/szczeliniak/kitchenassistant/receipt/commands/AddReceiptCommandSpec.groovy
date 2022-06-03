package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.Author
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.ReceiptFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

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
        return new NewReceiptDto(1, "", null, "", "", "", Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet())
    }

    private static Receipt receipt() {
        return new Receipt(1, 2, "", "", new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), "", false, null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
