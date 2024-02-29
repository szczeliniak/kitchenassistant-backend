package pl.szczeliniak.kitchenassistant.recipe

import org.mapstruct.Mapper
import pl.szczeliniak.kitchenassistant.recipe.db.Ingredient
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroup
import pl.szczeliniak.kitchenassistant.recipe.dto.response.IngredientGroupResponse

@Mapper
abstract class IngredientGroupMapper {

    abstract fun map(ingredientGroup: IngredientGroup): IngredientGroupResponse.IngredientGroupDto

    abstract fun map(ingredient: Ingredient): IngredientGroupResponse.IngredientGroupDto.IngredientDto

}