package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteCategoryCommandSpec extends Specification {

    def categoryDao = Mock(CategoryDao)
    def receiptDao = Mock(ReceiptDao)

    @Subject
    def deleteCategoryCommand = new DeleteCategoryCommand(categoryDao, receiptDao)

    def 'should delete category'() {
        given:
        def category = category(false)
        def receipt = receipt()

        categoryDao.findById(1) >> category
        categoryDao.save(category) >> category
        receiptDao.findAll(new ReceiptCriteria(3, 1, null, null), null, null) >> List.of(receipt)

        when:
        def result = deleteCategoryCommand.execute(1)

        then:
        category.deleted
        receipt.category == null
        result == new SuccessResponse(1)
        1 * receiptDao.save(Set.of(receipt))
    }

    def 'should throw exception when category not found'() {
        given:
        categoryDao.findById(1) >> null

        when:
        deleteCategoryCommand.execute(1)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Category not found"
    }

    def 'should throw exception when category is already marked as deleted'() {
        given:
        def category = category(true)
        categoryDao.findById(1) >> category

        when:
        deleteCategoryCommand.execute(1)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Category is already marked as deleted!"
    }

    private static Category category(boolean deleted) {
        return new Category(1, '', 3, 2, deleted, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Receipt receipt() {
        return new Receipt(10, "", 1, "", null, null, false, category(false), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
