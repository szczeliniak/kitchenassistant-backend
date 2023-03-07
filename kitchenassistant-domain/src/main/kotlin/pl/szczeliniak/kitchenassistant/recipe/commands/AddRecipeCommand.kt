package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewRecipeDto
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.RecipeFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AddRecipeCommand(private val recipeDao: RecipeDao, private val recipeFactory: RecipeFactory) {

    fun execute(dto: NewRecipeDto): SuccessResponse {
        return SuccessResponse(recipeDao.save(recipeFactory.create(dto)).id)
    }

}