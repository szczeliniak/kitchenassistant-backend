package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.dayplan.commands.DeleteRecipesFromDayPlansCommand
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.DeleteRecipeFromShoppingListsCommand

class DeleteRecipeCommand(
        private val recipeDao: RecipeDao,
        private val deleteRecipesFromDayPlansCommand: DeleteRecipesFromDayPlansCommand,
        private val deleteRecipeFromShoppingListsCommand: DeleteRecipeFromShoppingListsCommand
) {

    fun execute(id: Int): SuccessResponse {
        val recipe =
            recipeDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        if (recipe.deleted) {
            throw KitchenAssistantException(ErrorCode.RECIPE_ALREADY_REMOVED)
        }
        recipe.deleted = true

        deleteRecipesFromDayPlansCommand.execute(id)
        deleteRecipeFromShoppingListsCommand.execute(id)

        return SuccessResponse(recipeDao.save(recipe).id)
    }

}