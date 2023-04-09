package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.commands.dto.UpdateCategoryDto
import pl.szczeliniak.kitchenassistant.recipe.db.Category
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class UpdateCategoryCommandSpec extends Specification {

    def categoryDao = Mock(CategoryDao)

    @Subject
    def updateCategoryCommand = new UpdateCategoryCommand(categoryDao)

    def 'should update category'() {
        given:
        def category = category()
        categoryDao.findById(1) >> category
        categoryDao.save(category) >> category

        when:
        def result = updateCategoryCommand.execute(1, updateCategoryDto())

        then:
        category.name == "NAME"
        result == new SuccessResponse(1)
    }

    private static UpdateCategoryDto updateCategoryDto() {
        return new UpdateCategoryDto("NAME", 1)
    }

    private static Category category() {
        return new Category(1, "", 1, 1, ZonedDateTime.now(), ZonedDateTime.now())
    }

}