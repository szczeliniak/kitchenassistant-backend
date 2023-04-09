package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.dayplan.commands.DeleteRecipeFromDayPlansCommand
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.DeleteRecipeFromShoppingListsCommand

class DeleteRecipeCommand(
    private val recipeDao: RecipeDao,
    private val deleteRecipeFromDayPlansCommand: DeleteRecipeFromDayPlansCommand,
    private val deleteRecipeFromShoppingListsCommand: DeleteRecipeFromShoppingListsCommand
) {

    fun execute(id: Int): SuccessResponse {
        recipeDao.findById(id)?.let {
            deleteRecipeFromDayPlansCommand.execute(id)
            deleteRecipeFromShoppingListsCommand.execute(id)
            recipeDao.delete(it)
        }
        return SuccessResponse(id)
    }

}