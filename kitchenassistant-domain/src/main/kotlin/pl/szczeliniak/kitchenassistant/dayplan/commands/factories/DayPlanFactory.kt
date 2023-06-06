package pl.szczeliniak.kitchenassistant.dayplan.commands.factories

import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.AddRecipeToDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlan
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.user.db.UserDao

open class DayPlanFactory(
    private val userDao: UserDao
) {

    open fun create(dto: AddRecipeToDayPlanDto): DayPlan {
        return DayPlan(
            user = userDao.findById(dto.userId) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND),
            date = dto.date,
            automaticArchiving = false
        )
    }

}