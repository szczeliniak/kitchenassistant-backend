package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.Category
import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.CategoryDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class GetCategoriesQuerySpec extends Specification {

    private def categoryDao = Mock(CategoryDao)

    @Subject
    private def getCategoriesQuery = new GetCategoriesQuery(categoryDao)

    def 'should return categories'() {

    }

    private static Category category() {
        return new Category(0, "NAME", 1, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static CategoryDto categoryDto() {
        return new CategoryDto(0, "NAME")
    }

}
