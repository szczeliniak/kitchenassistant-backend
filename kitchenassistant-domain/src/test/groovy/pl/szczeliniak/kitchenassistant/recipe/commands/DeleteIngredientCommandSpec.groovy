package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.db.User
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteIngredientCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)
    def ingredientDao = Mock(IngredientDao)

    @Subject
    def deleteIngredientCommand = new DeleteIngredientCommand(recipeDao, ingredientDao)

    def 'should delete ingredient'() {
        given:
        def ingredient = ingredient()
        def ingredientGroup = ingredientGroup(ingredient)
        def recipe = recipe(ingredientGroup)
        recipeDao.findById(1) >> recipe

        when:
        def result = deleteIngredientCommand.execute(1, 2, 3)

        then:
        result == new SuccessResponse(3)
        1 * ingredientDao.delete(ingredient)
    }

    private static Recipe recipe(IngredientGroup ingredientGroup) {
        return new Recipe(1, '', new User(2, "", "", "", ZonedDateTime.now(), ZonedDateTime.now()), '', new Author(2, "", new User(1, "", "", "", ZonedDateTime.now(), ZonedDateTime.now()), ZonedDateTime.now(), ZonedDateTime.now()), '', false, null, Set.of(ingredientGroup), Collections.emptySet(), null, Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Ingredient ingredient() {
        return new Ingredient(3, '', '', ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static IngredientGroup ingredientGroup(Ingredient ingredient) {
        return new IngredientGroup(2, "GROUP_NAME", new HashSet<Ingredient>(Collections.singleton(ingredient)), ZonedDateTime.now(), ZonedDateTime.now())
    }

}
