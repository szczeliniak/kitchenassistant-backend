package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.dayplan.db.*
import pl.szczeliniak.kitchenassistant.dayplan.mapper.DayPlanMapperImpl
import pl.szczeliniak.kitchenassistant.dayplan.mapper.RecipeSnapshotMapperImpl
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.RequestContext

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
        ingredientSnapshotDao: IngredientSnapshotDao
    ) = DayPlanService(
        DayPlanMapperImpl(),
        RecipeSnapshotMapperImpl(),
        dayPlanDao,
        recipeDao,
        requestContext,
        recipeSnapshotDao,
        ingredientGroupSnapshotDao,
        stepSnapshotDao,
        ingredientSnapshotDao
    )

}