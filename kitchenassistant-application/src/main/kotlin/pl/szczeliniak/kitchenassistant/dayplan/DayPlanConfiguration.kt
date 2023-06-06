package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.dayplan.commands.*
import pl.szczeliniak.kitchenassistant.dayplan.commands.factories.DayPlanFactory
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.queries.DayPlanConverter
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlanQuery
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlansQuery
import pl.szczeliniak.kitchenassistant.recipe.RecipeFacade
import pl.szczeliniak.kitchenassistant.recipe.queries.GetRecipeQuery
import pl.szczeliniak.kitchenassistant.user.db.UserDao

@Configuration
class DayPlanConfiguration {

    @Bean
    fun dayPlanFacade(
        dayPlanDao: DayPlanDao,
        recipeFacade: RecipeFacade,
        getRecipeQuery: GetRecipeQuery,
        userDao: UserDao
    ): DayPlanFacade {
        val dayPlanConverter = DayPlanConverter(recipeFacade)
        return DayPlanFacade(
            GetDayPlanQuery(dayPlanDao, dayPlanConverter),
            GetDayPlansQuery(dayPlanDao, dayPlanConverter),
            AddRecipeToDayPlanCommand(dayPlanDao, recipeFacade, DayPlanFactory(userDao)),
            DeleteRecipeFromDayPlanCommand(dayPlanDao),
            DeleteDayPlanCommand(dayPlanDao),
            ArchiveDayPlanCommand(dayPlanDao),
            UpdateDayPlanCommand(dayPlanDao),
            ArchiveDayPlansCommand(dayPlanDao)
        )
    }

}