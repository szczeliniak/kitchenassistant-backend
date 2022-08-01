package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.recipe.Ingredient
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewIngredientDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class IngredientFactorySpec extends Specification {

    @Subject
    def ingredientFactory = new IngredientFactory()

    def 'should create ingredient'() {
        when:
        def result = ingredientFactory.create(newIngredientDto())

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt", "modifiedAt")
                .isEqualTo(ingredient())
    }

    private static NewIngredientDto newIngredientDto() {
        return new NewIngredientDto("NAME", "QUANTITY")
    }

    private static Ingredient ingredient() {
        return new Ingredient(0, "NAME", "QUANTITY", false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}