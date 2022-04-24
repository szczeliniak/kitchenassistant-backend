package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.receipt.Category
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewCategoryDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class CategoryFactoryTest extends Specification {

    @Subject
    def categoryFactory = new CategoryFactory()

    def 'should create category'() {

        when:
        def result = categoryFactory.create(newCategoryDto())

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt_", "modifiedAt_")
                .isEqualTo(category())
    }

    private static Category category() {
        return new Category(0, "NAME", 1, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static NewCategoryDto newCategoryDto() {
        return new NewCategoryDto("NAME", 1)
    }
}
