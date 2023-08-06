package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.RequestContext

@Configuration
class DayPlanConfiguration {

    @Bean
    fun dayPlanFacade(
        dayPlanDao: DayPlanDao,
        recipeDao: RecipeDao,
        requestContext: RequestContext
    ): DayPlanService = DayPlanService(DayPlanMapperImpl(), dayPlanDao, recipeDao, requestContext)

}