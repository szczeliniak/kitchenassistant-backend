package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.dayplan.commands.DeleteRecipeFromDayPlansCommand
import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.DeleteRecipeFromShoppingListsCommand
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class DeleteRecipeCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)
    def deassignRecipeFromShoppingListsCommand = Mock(DeleteRecipeFromShoppingListsCommand)
    def deassignRecipesFromDayPlansCommand = Mock(DeleteRecipeFromDayPlansCommand)

    @Subject
    def deleteRecipeCommand = new DeleteRecipeCommand(recipeDao, deassignRecipesFromDayPlansCommand, deassignRecipeFromShoppingListsCommand)

    def 'should delete recipe'() {
        given:
        def recipe = recipe()
        recipeDao.findById(1) >> recipe

        when:
        def result = deleteRecipeCommand.execute(1)

        then:
        result == new SuccessResponse(1)
        1 * deassignRecipeFromShoppingListsCommand.execute(1)
        1 * deassignRecipesFromDayPlansCommand.execute(1)
        1 * recipeDao.delete(recipe)
    }

    private static Recipe recipe() {
        return new Recipe(1, '', 2, '', new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), '', false, null, Collections.emptySet(), Collections.emptySet(), null, Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

}
