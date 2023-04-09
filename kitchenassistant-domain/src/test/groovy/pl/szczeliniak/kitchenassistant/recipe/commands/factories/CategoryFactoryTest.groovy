package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewCategoryDto
import pl.szczeliniak.kitchenassistant.recipe.db.Category
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class CategoryFactoryTest extends Specification {

    @Subject
    def categoryFactory = new CategoryFactory()

    def 'should create category'() {

        when:
        def result = categoryFactory.create(newCategoryDto())

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt", "modifiedAt")
                .isEqualTo(category())
    }

    private static Category category() {
        return new Category(0, "NAME", 1, 3, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static NewCategoryDto newCategoryDto() {
        return new NewCategoryDto("NAME", 1, 3)
    }
}
