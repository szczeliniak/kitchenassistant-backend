package pl.szczeliniak.kitchenassistant.dayplan.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import pl.szczeliniak.kitchenassistant.dayplan.db.IngredientGroupSnapshot
import pl.szczeliniak.kitchenassistant.dayplan.db.IngredientSnapshot
import pl.szczeliniak.kitchenassistant.dayplan.db.RecipeSnapshot
import pl.szczeliniak.kitchenassistant.dayplan.db.StepSnapshot
import pl.szczeliniak.kitchenassistant.recipe.db.Ingredient
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroup
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.Step

@Mapper
abstract class RecipeSnapshotMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(source = "recipe.id", target = "originalRecipeId")
    abstract fun map(recipe: Recipe): RecipeSnapshot

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(source = "ingredientGroup.id", target = "originalIngredientGroupId")
    abstract fun map(ingredientGroup: IngredientGroup): IngredientGroupSnapshot

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(source = "ingredient.id", target = "originalIngredientId")
    @Mapping(target = "checked", expression = "java(false)")
    abstract fun map(ingredient: Ingredient): IngredientSnapshot

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(source = "step.id", target = "originalStepId")
    abstract fun map(step: Step): StepSnapshot
}