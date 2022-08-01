package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.IngredientGroup
import pl.szczeliniak.kitchenassistant.recipe.Recipe
import pl.szczeliniak.kitchenassistant.recipe.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteIngredientGroupCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)
    @Subject
    def deleteIngredientGroupCommand = new DeleteIngredientGroupCommand(recipeDao)

    def 'should delete ingredient group'() {
        given:
        def ingredientGroup = ingredientGroup()
        def recipe = recipe(ingredientGroup)
        recipeDao.findById(1) >> recipe
        recipeDao.save(recipe) >> recipe

        when:
        def result = deleteIngredientGroupCommand.execute(1, 2)

        then:
        ingredientGroup.deleted
        result == new SuccessResponse(2)
    }

    private static Recipe recipe(IngredientGroup ingredientGroup) {
        return new Recipe(1, "", 1, "", null, "", false, null, Collections.singleton(ingredientGroup), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static IngredientGroup ingredientGroup() {
        return new IngredientGroup(2, "NAME", Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
