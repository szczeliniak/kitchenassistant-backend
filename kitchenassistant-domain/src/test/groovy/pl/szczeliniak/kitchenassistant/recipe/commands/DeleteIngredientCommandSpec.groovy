package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteIngredientCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)

    @Subject
    def deleteIngredientCommand = new DeleteIngredientCommand(recipeDao)

    def 'should delete ingredient'() {
        given:
        def recipe = recipe()
        recipeDao.findById(1) >> recipe

        when:
        def result = deleteIngredientCommand.execute(1, 2, 3)

        then:
        result == new SuccessResponse(3)
        recipe.ingredientGroups[0].ingredients.isEmpty()
        1 * recipeDao.save(recipe)
    }

    private static Recipe recipe() {
        return new Recipe(1, '', 2, '', new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), '', false, null, Set.of(ingredientGroup()), Collections.emptySet(), null, Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Ingredient ingredient() {
        return new Ingredient(3, '', '', ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static IngredientGroup ingredientGroup() {
        return new IngredientGroup(2, "GROUP_NAME", new HashSet<Ingredient>(Collections.singleton(ingredient())), ZonedDateTime.now(), ZonedDateTime.now())
    }

}
