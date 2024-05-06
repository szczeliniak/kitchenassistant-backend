package pl.szczeliniak.cookbook.dayplan

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import pl.szczeliniak.cookbook.dayplan.db.IngredientGroupSnapshot
import pl.szczeliniak.cookbook.dayplan.db.IngredientSnapshot
import pl.szczeliniak.cookbook.dayplan.db.RecipeSnapshot
import pl.szczeliniak.cookbook.dayplan.db.StepGroupSnapshot
import pl.szczeliniak.cookbook.dayplan.db.StepSnapshot
import pl.szczeliniak.cookbook.recipe.db.Ingredient
import pl.szczeliniak.cookbook.recipe.db.IngredientGroup
import pl.szczeliniak.cookbook.recipe.db.Recipe
import pl.szczeliniak.cookbook.recipe.db.Step
import pl.szczeliniak.cookbook.recipe.db.StepGroup

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
    @Mapping(source = "stepGroup.id", target = "originalStepGroupId")
    abstract fun map(stepGroup: StepGroup): StepGroupSnapshot

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