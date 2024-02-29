package pl.szczeliniak.kitchenassistant.dayplan

import org.mapstruct.Mapper
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.db.IngredientGroupSnapshot
import pl.szczeliniak.kitchenassistant.dayplan.db.IngredientSnapshot
import pl.szczeliniak.kitchenassistant.dayplan.db.RecipeSnapshot
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlansResponse
import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.Category

@Mapper
abstract class DayPlanMapper {

    abstract fun map(dayPlan: DayPlan): DayPlansResponse.DayPlanDto

    abstract fun mapDetails(dayPlan: DayPlan): DayPlanResponse.DayPlanDto

    abstract fun map(recipe: RecipeSnapshot): DayPlanResponse.DayPlanDto.RecipeDto

    fun toName(author: Author?): String? {
        return author?.name
    }

    fun toName(category: Category?): String? {
        return category?.name
    }

    abstract fun map(ingredientGroup: IngredientGroupSnapshot): DayPlanResponse.DayPlanDto.RecipeDto.IngredientGroupDto

    abstract fun map(ingredient: IngredientSnapshot): DayPlanResponse.DayPlanDto.RecipeDto.IngredientGroupDto.IngredientDto
}