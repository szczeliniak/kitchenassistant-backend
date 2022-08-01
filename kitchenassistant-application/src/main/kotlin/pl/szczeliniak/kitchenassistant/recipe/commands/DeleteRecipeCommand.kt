package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.dayplan.commands.DeassignRecipesFromDayPlansCommand
import pl.szczeliniak.kitchenassistant.recipe.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.DeassignRecipeFromShoppingListsCommand

class DeleteRecipeCommand(
    private val recipeDao: RecipeDao,
    private val deassignRecipesFromDayPlansCommand: DeassignRecipesFromDayPlansCommand,
    private val deassignRecipeFromShoppingListsCommand: DeassignRecipeFromShoppingListsCommand
) {

    fun execute(id: Int): SuccessResponse {
        val recipe =
            recipeDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        if (recipe.deleted) {
            throw KitchenAssistantException(ErrorCode.RECIPE_ALREADY_REMOVED)
        }
        recipe.deleted = true

        deassignRecipesFromDayPlansCommand.execute(id)
        deassignRecipeFromShoppingListsCommand.execute(id)

        return SuccessResponse(recipeDao.save(recipe).id)
    }

}