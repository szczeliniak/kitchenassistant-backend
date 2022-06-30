package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.stereotype.Component

@Component
class DayPlanMapper {

    fun toDomain(entity: DayPlanEntity): DayPlan {
        return DayPlan(
            entity.id,
            entity.userId,
            entity.date,
            entity.receiptIds.toMutableSet(),
            entity.deleted,
            entity.archived,
            entity.createdAt,
            entity.modifiedAt
        )
    }

    fun toEntity(dayPlan: DayPlan): DayPlanEntity {
        return DayPlanEntity(
            dayPlan.id,
            dayPlan.userId,
            dayPlan.date,
            dayPlan.receiptIds.toMutableSet(),
            dayPlan.deleted,
            dayPlan.archived,
            dayPlan.createdAt,
            dayPlan.modifiedAt
        )
    }

}