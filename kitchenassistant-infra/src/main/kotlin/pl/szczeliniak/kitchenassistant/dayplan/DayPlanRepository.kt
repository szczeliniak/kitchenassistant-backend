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
        criteria.userId?.let { builder.append(" AND dp.userId = :userId") }
        criteria.archived?.let { builder.append(" AND dp.archived = :archived") }
        criteria.since?.let { builder.append(" AND dp.date IS NOT NULL AND dp.date >= :since") }
        criteria.to?.let { builder.append(" AND dp.date IS NOT NULL AND dp.date <= :to") }
        criteria.name?.let { builder.append(" AND LOWER(dp.name) LIKE LOWER(:name)") }
        criteria.automaticArchiving?.let { builder.append(" AND dp.automaticArchiving = :automaticArchiving") }
        return builder.toString()
    }

    private fun <T> applyParameters(
        criteria: DayPlanCriteria,
        typedQuery: TypedQuery<T>
    ): TypedQuery<T> {
        var query = typedQuery
        criteria.userId?.let { query = typedQuery.setParameter("userId", it) }
        criteria.archived?.let { query = typedQuery.setParameter("archived", it) }
        criteria.since?.let { query = typedQuery.setParameter("since", it) }
        criteria.to?.let { query = typedQuery.setParameter("to", criteria.to) }
        criteria.name?.let { query = typedQuery.setParameter("name", "%" + criteria.name + "%") }
        criteria.automaticArchiving?.let { query = typedQuery.setParameter("automaticArchiving", it) }
        return query
    }

}