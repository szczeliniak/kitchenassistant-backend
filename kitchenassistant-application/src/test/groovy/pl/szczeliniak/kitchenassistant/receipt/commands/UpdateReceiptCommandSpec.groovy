package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.Category
import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateReceiptDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class UpdateReceiptCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def categoryDao = Mock(CategoryDao)

    @Subject
    def updateReceiptCommand = new UpdateReceiptCommand(receiptDao, categoryDao)

    def 'should update receipt'() {
        given:
        def receipt = receipt()
        def newCategory = category(3)
        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt
        categoryDao.findById(3) >> newCategory

        when:
        def result = updateReceiptCommand.execute(1, updateReceiptDto())

        then:
        receipt.name == "NAME"
        receipt.description == "DESC"
        receipt.author == "AUTHOR"
        receipt.source == "SOURCE"
        receipt.userId == 2
        receipt.category == newCategory
        result == new SuccessResponse(1)
    }

    private static UpdateReceiptDto updateReceiptDto() {
        return new UpdateReceiptDto(2, "NAME", 3, "DESC", "AUTHOR", "SOURCE")
    }

    private static Receipt receipt() {
        return new Receipt(1, 1, "", "", "", "", category(0), Collections.emptyList(),
                Collections.emptyList(), false, LocalDateTime.now(), LocalDateTime.now())
    }

    static Category category(Integer id) {
        return new Category(id, "", 3, false,
                LocalDateTime.of(2000, 1, 1, 1, 1),
                LocalDateTime.of(2000, 1, 1, 1, 2))
    }
}
