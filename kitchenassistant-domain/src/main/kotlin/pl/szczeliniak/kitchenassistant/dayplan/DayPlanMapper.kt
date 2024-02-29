package pl.szczeliniak.kitchenassistant.dayplan

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.db.IngredientGroupSnapshot
import pl.szczeliniak.kitchenassistant.dayplan.db.IngredientSnapshot
import pl.szczeliniak.kitchenassistant.dayplan.db.RecipeSnapshot
import pl.szczeliniak.kitchenassistant.dayplan.db.StepSnapshot
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlansResponse

@Mapper
abstract class DayPlanMapper {

    abstract fun map(dayPlan: DayPlan): DayPlansResponse.DayPlan

    @Mapping(source = "recipes", target = "recipes")
    abstract fun mapDetails(dayPlan: DayPlan, recipes: List<DayPlanResponse.DayPlan.Recipe>): DayPlanResponse.DayPlan

    abstract fun map(recipe: RecipeSnapshot, author: String?, category: String?): DayPlanResponse.DayPlan.Recipe

    abstract fun map(ingredientGroup: IngredientGroupSnapshot): DayPlanResponse.DayPlan.Recipe.IngredientGroup

    abstract fun map(ingredient: IngredientSnapshot): DayPlanResponse.DayPlan.Recipe.IngredientGroup.Ingredient

    abstract fun map(step: StepSnapshot): DayPlanResponse.DayPlan.Recipe.Step
}