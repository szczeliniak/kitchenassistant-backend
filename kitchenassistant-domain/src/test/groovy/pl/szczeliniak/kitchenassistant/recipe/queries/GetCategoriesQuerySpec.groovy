package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.db.Category
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.CategoriesResponse
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.CategoryDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class GetCategoriesQuerySpec extends Specification {

    private def categoryDao = Mock(CategoryDao)
    private def recipeConverter = Mock(RecipeConverter)

    @Subject
    private def getCategoriesQuery = new GetCategoriesQuery(categoryDao, recipeConverter)

    def 'should return categories'() {
        given:
        def criteria = new CategoryCriteria(1)
        def category = category()
        def categoryDto = categoryDto()
        categoryDao.findAll(criteria) >> Collections.singletonList(category)
        recipeConverter.map(category) >> categoryDto

        when:
        def categories = getCategoriesQuery.execute(criteria)

        then:
        categories == new CategoriesResponse(Set.of(categoryDto))
    }

    private static Category category() {
        return new Category(0, "NAME", 1, 2, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static CategoryDto categoryDto() {
        return new CategoryDto(0, "NAME", 2)
    }

}
