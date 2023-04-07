package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
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
        def ingredient = ingredient(false)
        def ingredientGroup = ingredientGroup(Set.of(ingredient))
        def recipe = recipe(Set.of(ingredientGroup))
        recipeDao.findById(1) >> recipe
        recipeDao.save(recipe) >> recipe

        when:
        def result = deleteIngredientCommand.execute(1, 2, 3)

        then:
        ingredient.deleted
        result == new SuccessResponse(3)
    }

    def 'should throw exception when ingredient group not found'() {
        given:
        def recipe = recipe(Collections.emptySet())
        recipeDao.findById(1) >> recipe

        when:
        deleteIngredientCommand.execute(1, 2, 3)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Ingredient group not found"
    }

    def 'should throw exception when ingredient not found'() {
        given:
        def recipe = recipe(Collections.singleton(ingredientGroup(Collections.emptySet())))
        recipeDao.findById(1) >> recipe

        when:
        deleteIngredientCommand.execute(1, 2, 3)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Ingredient not found"
    }

    def 'should throw exception when ingredient is already marked as deleted'() {
        given:
        def ingredient = ingredient(true)
        def ingredientGroup = ingredientGroup(Set.of(ingredient))
        def recipe = recipe(Set.of(ingredientGroup))
        recipeDao.findById(1) >> recipe

        when:
        deleteIngredientCommand.execute(1, 2, 3)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Ingredient is already marked as deleted!"
    }

    private static Recipe recipe(Set<IngredientGroup> ingredientGroups) {
        return new Recipe(1, '', 2, '', new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), '', false, null, ingredientGroups, Collections.emptySet(), null, Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Ingredient ingredient(boolean deleted) {
        return new Ingredient(3, '', '', deleted, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static IngredientGroup ingredientGroup(Set<Ingredient> ingredients) {
        return new IngredientGroup(2, "GROUP_NAME", ingredients, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
