package pl.szczeliniak.cookbook.dayplan

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import pl.szczeliniak.cookbook.dayplan.db.DayPlan
import pl.szczeliniak.cookbook.dayplan.db.IngredientGroupSnapshot
import pl.szczeliniak.cookbook.dayplan.db.IngredientSnapshot
import pl.szczeliniak.cookbook.dayplan.db.RecipeSnapshot
import pl.szczeliniak.cookbook.dayplan.dto.response.DayPlanResponse
import pl.szczeliniak.cookbook.dayplan.dto.response.DayPlansResponse

@Mapper
abstract class DayPlanMapper {

    abstract fun map(dayPlan: DayPlan): DayPlansResponse.DayPlan

    abstract fun map(recipe: RecipeSnapshot): DayPlansResponse.DayPlan.Recipe

    @Mapping(source = "recipes", target = "recipes")
    abstract fun mapDetails(dayPlan: DayPlan, recipes: List<DayPlanResponse.DayPlan.Recipe>): DayPlanResponse.DayPlan

    abstract fun map(recipe: RecipeSnapshot, author: String?, category: String?): DayPlanResponse.DayPlan.Recipe

    abstract fun map(ingredientGroup: IngredientGroupSnapshot): DayPlanResponse.DayPlan.Recipe.IngredientGroup

    abstract fun map(ingredient: IngredientSnapshot): DayPlanResponse.DayPlan.Recipe.IngredientGroup.Ingredient

}