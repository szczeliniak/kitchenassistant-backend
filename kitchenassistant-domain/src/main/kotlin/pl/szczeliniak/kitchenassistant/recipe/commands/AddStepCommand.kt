package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.db.StepDao
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewStepDto
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.StepFactory
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AddStepCommand(
        private val recipeDao: RecipeDao,
        private val stepDao: StepDao,
        private val stepFactory: StepFactory
) {

    fun execute(recipeId: Int, dto: NewStepDto): SuccessResponse {
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        val step = stepDao.save(stepFactory.create(dto))
        recipe.steps.add(step)
        recipeDao.save(recipe)
        return SuccessResponse(step.id)
    }

}