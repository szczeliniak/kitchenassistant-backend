package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroup
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
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
        def recipe = recipe()
        recipeDao.findById(1) >> recipe

        when:
        def result = deleteIngredientGroupCommand.execute(1, 2)

        then:
        1 * recipeDao.save(recipe)
        recipe.ingredientGroups.isEmpty()
        result == new SuccessResponse(2)
    }

    private static Recipe recipe() {
        return new Recipe(1, "", 1, "", null, "", false, null, new HashSet<>(Collections.singleton(ingredientGroup())), Collections.emptySet(), null, Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static IngredientGroup ingredientGroup() {
        return new IngredientGroup(2, "NAME", Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

}
