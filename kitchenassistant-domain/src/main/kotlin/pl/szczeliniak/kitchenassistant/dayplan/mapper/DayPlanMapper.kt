package pl.szczeliniak.kitchenassistant.dayplan.mapper

import org.mapstruct.Mapper
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlansResponse
import pl.szczeliniak.kitchenassistant.recipe.db.*

@Mapper
abstract class DayPlanMapper {

    abstract fun map(dayPlan: DayPlan): DayPlansResponse.DayPlanDto

    abstract fun mapDetails(dayPlan: DayPlan): DayPlanResponse.DayPlanDto

    abstract fun map(recipe: Recipe): DayPlanResponse.DayPlanDto.RecipeDto

    fun toName(author: Author): String {
        return author.name
    }

    fun toName(category: Category?): String? {
        return category?.name
    }

    abstract fun map(ingredientGroup: IngredientGroup): DayPlanResponse.DayPlanDto.RecipeDto.IngredientGroupDto

    abstract fun map(ingredient: Ingredient): DayPlanResponse.DayPlanDto.RecipeDto.IngredientGroupDto.IngredientDto
}