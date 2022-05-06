package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.Category
import pl.szczeliniak.kitchenassistant.receipt.CategoryCriteria
import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.CategoriesResponse
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.CategoryDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class GetCategoriesQuerySpec extends Specification {

    private def categoryDao = Mock(CategoryDao)
    private def receiptConverter = Mock(ReceiptConverter)

    @Subject
    private def getCategoriesQuery = new GetCategoriesQuery(categoryDao, receiptConverter)

    def 'should return categories'() {
        given:
        def criteria = new CategoryCriteria(1)
        def category = category()
        def categoryDto = categoryDto()
        categoryDao.findAll(criteria) >> Collections.singletonList(category)
        receiptConverter.map(category) >> categoryDto

        when:
        def categories = getCategoriesQuery.execute(criteria)

        then:
        categories == new CategoriesResponse(Set.of(categoryDto))
    }

    private static Category category() {
        return new Category(0, "NAME", 1, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static CategoryDto categoryDto() {
        return new CategoryDto(0, "NAME")
    }

}
