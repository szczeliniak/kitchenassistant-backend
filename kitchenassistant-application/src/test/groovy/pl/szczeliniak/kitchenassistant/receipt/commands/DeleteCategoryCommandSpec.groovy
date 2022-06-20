package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.Category
import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteCategoryCommandSpec extends Specification {

    def categoryDao = Mock(CategoryDao)
    @Subject
    def deleteCategoryCommand = new DeleteCategoryCommand(categoryDao)

    def 'should delete category'() {
        given:
        def category = category(false)
        categoryDao.findById(1) >> category
        categoryDao.save(category) >> category

        when:
        def result = deleteCategoryCommand.execute(1)

        then:
        category.deleted
        result == new SuccessResponse(1)
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
        return new Category(1, '', 1, 2, deleted, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
