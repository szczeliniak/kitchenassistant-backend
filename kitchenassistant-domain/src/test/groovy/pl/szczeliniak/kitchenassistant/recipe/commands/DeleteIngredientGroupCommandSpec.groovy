package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteIngredientGroupCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)
    def ingredientGroupDao = Mock(IngredientGroupDao)
    def ingredientDao = Mock(IngredientDao)
    @Subject
    def deleteIngredientGroupCommand = new DeleteIngredientGroupCommand(recipeDao, ingredientGroupDao, ingredientDao)

    def 'should delete ingredient group'() {
        given:
        def ingredient = ingredient()
        def ingredientGroup = ingredientGroup(ingredient)
        def recipe = recipe(ingredientGroup)
        recipeDao.findById(1) >> recipe

        when:
        def result = deleteIngredientGroupCommand.execute(1, 2)

        then:
        1 * ingredientDao.delete(ingredient)
        1 * ingredientGroupDao.delete(ingredientGroup)
        result == new SuccessResponse(2)
    }

    private static Recipe recipe(IngredientGroup ingredientGroup) {
        return new Recipe(1, "", 1, "", null, "", false, null, new HashSet<>(Collections.singleton(ingredientGroup)), Collections.emptySet(), null, Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static IngredientGroup ingredientGroup(Ingredient ingredient) {
        return new IngredientGroup(2, "NAME", new HashSet<Ingredient>(Collections.singleton(ingredient)), ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Ingredient ingredient() {
        return new Ingredient(3, "NAME", "QUANTITY", ZonedDateTime.now(), ZonedDateTime.now())
    }

}
