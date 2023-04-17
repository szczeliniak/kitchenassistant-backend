package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.recipe.db.Ingredient
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class IngredientFactorySpec extends Specification {

    @Subject
    def ingredientFactory = new IngredientFactory()

    def 'should create ingredient'() {
        when:
        def result = ingredientFactory.create("NAME", "QUANTITY")

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt", "modifiedAt")
                .isEqualTo(ingredient())
    }

    private static Ingredient ingredient() {
        return new Ingredient(0, "NAME", "QUANTITY", ZonedDateTime.now(), ZonedDateTime.now())
    }

}