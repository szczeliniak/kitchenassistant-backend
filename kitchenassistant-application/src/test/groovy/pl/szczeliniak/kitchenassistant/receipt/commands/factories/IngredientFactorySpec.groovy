package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.enums.IngredientUnit
import pl.szczeliniak.kitchenassistant.receipt.Ingredient
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class IngredientFactorySpec extends Specification {

    @Subject
    def ingredientFactory = new IngredientFactory()

    def 'should create ingredient'() {
        when:
        def result = ingredientFactory.create(newIngredientDto())

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt_", "modifiedAt_")
                .isEqualTo(ingredient())
    }

    private static NewIngredientDto newIngredientDto() {
        return new NewIngredientDto("NAME", "QUANTITY", IngredientUnit.CUPS)
    }

    private static Ingredient ingredient() {
        return new Ingredient(0, "NAME", "QUANTITY", IngredientUnit.CUPS, false, LocalDateTime.now(), LocalDateTime.now())
    }

}