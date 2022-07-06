package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.stereotype.Repository
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class DayPlanRepository(@PersistenceContext private val entityManager: EntityManager) : DayPlanDao {

    @Transactional
    override fun save(dayPlan: DayPlan): DayPlan {
        entityManager.persist(dayPlan)
        return dayPlan
    }

    @Transactional
    override fun save(dayPlans: Set<DayPlan>) {
        dayPlans.forEach { save(it) }
    }

    override fun findAll(criteria: DayPlanCriteria, offset: Int?, limit: Int?): Set<DayPlan> {
        val typedQuery = applyParameters(
            criteria,
            entityManager.createQuery(
                "SELECT dp FROM DayPlan dp WHERE dp.deleted = false" + prepareCriteria(criteria) + " ORDER BY dp.date DESC, dp.id ASC",
                DayPlan::class.java
            )
        )
        offset?.let { typedQuery.firstResult = it }
        limit?.let { typedQuery.maxResults = it }
        return typedQuery.resultList.toMutableSet()
    }

    override fun findById(id: Int): DayPlan? {
        return entityManager
            .createQuery(
                "SELECT dp FROM DayPlan dp WHERE dp.id = :id AND dp.deleted = false",
                DayPlan::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    override fun count(criteria: DayPlanCriteria): Long {
        return applyParameters(
            criteria,
            entityManager.createQuery(
                "SELECT DISTINCT COUNT(dp) FROM DayPlan dp WHERE dp.deleted = false" + prepareCriteria(criteria),
                Long::class.javaObjectType
            )
        ).singleResult
    }

    private fun prepareCriteria(criteria: DayPlanCriteria): String {
        val builder = StringBuilder().append("")
        if (criteria.userId != null) {
            builder.append(" AND dp.userId = :userId")
        }
        if (criteria.archived != null) {
            builder.append(" AND dp.archived = :archived")
        }
        if (criteria.date != null) {
            builder.append(" AND dp.date = :date")
        }
        return builder.toString()
    }

    private fun <T> applyParameters(
        criteria: DayPlanCriteria,
        typedQuery: TypedQuery<T>
    ): TypedQuery<T> {
        var query = typedQuery
        if (criteria.userId != null) {
            query = typedQuery.setParameter("userId", criteria.userId)
        }
        if (criteria.archived != null) {
            query = typedQuery.setParameter("archived", criteria.archived)
        }
        if (criteria.date != null) {
            query = typedQuery.setParameter("date", criteria.date)
        }
        return query
    }

}