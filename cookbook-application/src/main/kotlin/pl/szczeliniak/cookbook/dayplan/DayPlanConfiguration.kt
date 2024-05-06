package pl.szczeliniak.cookbook.dayplan

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.cookbook.dayplan.db.DayPlanDao
import pl.szczeliniak.cookbook.dayplan.db.IngredientGroupSnapshotDao
import pl.szczeliniak.cookbook.dayplan.db.IngredientSnapshotDao
import pl.szczeliniak.cookbook.dayplan.db.RecipeSnapshotDao
import pl.szczeliniak.cookbook.dayplan.db.StepGroupSnapshotDao
import pl.szczeliniak.cookbook.dayplan.db.StepSnapshotDao
import pl.szczeliniak.cookbook.recipe.db.RecipeDao
import pl.szczeliniak.cookbook.shared.RequestContext

@Configuration
class DayPlanConfiguration {

    @Bean
    fun dayPlanService(
        dayPlanDao: DayPlanDao,
        recipeDao: RecipeDao,
        requestContext: RequestContext,
        recipeSnapshotDao: RecipeSnapshotDao,
        ingredientGroupSnapshotDao: IngredientGroupSnapshotDao,
        stepSnapshotDao: StepSnapshotDao,
        ingredientSnapshotDao: IngredientSnapshotDao,
        stepGroupSnapshotDao: StepGroupSnapshotDao
    ) = DayPlanService(
        DayPlanMapperImpl(),
        RecipeSnapshotMapperImpl(),
        dayPlanDao,
        recipeDao,
        requestContext,
        recipeSnapshotDao,
        ingredientGroupSnapshotDao,
        stepSnapshotDao,
        ingredientSnapshotDao,
        stepGroupSnapshotDao
    )

}