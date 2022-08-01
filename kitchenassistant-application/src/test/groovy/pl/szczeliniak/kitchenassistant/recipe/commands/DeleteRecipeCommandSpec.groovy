package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.dayplan.commands.DeassignRecipesFromDayPlansCommand
import pl.szczeliniak.kitchenassistant.recipe.Author
import pl.szczeliniak.kitchenassistant.recipe.Recipe
import pl.szczeliniak.kitchenassistant.recipe.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.DeassignRecipeFromShoppingListsCommand
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteRecipeCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)
    def deassignRecipeFromShoppingListsCommand = Mock(DeassignRecipeFromShoppingListsCommand)
    def deassignRecipesFromDayPlansCommand = Mock(DeassignRecipesFromDayPlansCommand)

    @Subject
    def deleteRecipeCommand = new DeleteRecipeCommand(recipeDao, deassignRecipesFromDayPlansCommand, deassignRecipeFromShoppingListsCommand)

    def 'should delete recipe'() {
        given:
        def recipe = recipe(false)
        recipeDao.findById(1) >> recipe
        recipeDao.save(recipe) >> recipe

        when:
        def result = deleteRecipeCommand.execute(1)

        then:
        recipe.deleted
        result == new SuccessResponse(1)
        1 * deassignRecipeFromShoppingListsCommand.execute(1)
        1 * deassignRecipesFromDayPlansCommand.execute(1)
    }

    def 'should throw exception when recipe not found'() {
        given:
        recipeDao.findById(1) >> null

        when:
        deleteRecipeCommand.execute(1)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Recipe not found"
    }

    def 'should throw exception when recipe is already marked as deleted'() {
        given:
        def recipe = recipe(true)
        recipeDao.findById(1) >> recipe
        recipeDao.save(recipe) >> recipe

        when:
        deleteRecipeCommand.execute(1)

        then:
        def e = thrown(KitchenAssistantException)
        e.message == "Recipe is already marked as deleted!"
        e.message == "Recipe is already marked as deleted!"
    }

    private static Recipe recipe(boolean deleted) {
        return new Recipe(1, '', 2, '', new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), '', false, null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), deleted, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
