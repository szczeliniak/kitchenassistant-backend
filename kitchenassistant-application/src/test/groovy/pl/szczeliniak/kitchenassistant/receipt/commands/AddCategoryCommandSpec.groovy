package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.Category
import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewCategoryDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.CategoryFactory
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class AddCategoryCommandSpec extends Specification {

    private categoryDao = Mock(CategoryDao)
    private categoryFactory = Mock(CategoryFactory)

    @Subject
    private addCategoryCommand = new AddCategoryCommand(categoryDao, categoryFactory)

    def 'should create category'() {
        given:
        def dto = newCategoryDto()
        def category = category()
        categoryFactory.create(dto) >> category
        categoryDao.save(category) >> category

        when:
        def response = addCategoryCommand.execute(dto)

        then:
        response == new SuccessResponse(0)

    }

    private static NewCategoryDto newCategoryDto() {
        return new NewCategoryDto("NAME", 1)
    }

    private static Category category() {
        return new Category(0, "", 1, false, LocalDateTime.now(), LocalDateTime.now())
    }
}
