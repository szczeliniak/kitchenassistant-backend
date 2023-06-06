package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewCategoryDto
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.CategoryFactory
import pl.szczeliniak.kitchenassistant.recipe.db.Category
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.db.User
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

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
        return new NewCategoryDto("NAME", 1, 2)
    }

    private static Category category() {
        return new Category(0, "", new User(1, "", "", "", ZonedDateTime.now(), ZonedDateTime.now()), 2, ZonedDateTime.now(), ZonedDateTime.now())
    }
}
