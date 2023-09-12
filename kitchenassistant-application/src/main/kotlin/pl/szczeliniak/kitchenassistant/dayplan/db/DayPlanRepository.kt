package pl.szczeliniak.kitchenassistant.dayplan.db

import org.springframework.stereotype.Repository
import pl.szczeliniak.kitchenassistant.dayplan.dto.DayPlanCriteria
import java.time.LocalDate
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

    override fun findAll(criteria: DayPlanCriteria, offset: Int?, limit: Int?, userId: Int?): Set<DayPlan> {
        val typedQuery = applyParameters(
            criteria,
            entityManager.createQuery(
                "SELECT dp FROM DayPlan dp WHERE dp.id IS NOT NULL" + prepareCriteria(
                    criteria,
                    userId
                ) + " ORDER BY dp.date ASC, dp.id ASC",
                DayPlan::class.java
            ), userId
        )
        offset?.let { typedQuery.firstResult = it }
        limit?.let { typedQuery.maxResults = it }

        return typedQuery.resultList.toMutableSet()
    }

    @Transactional
    override fun delete(id: Int, userId: Int?): Boolean {
        var query = "DELETE FROM DayPlan dp WHERE dp.id = :id"
        userId?.let { query += " AND dp.userId = :userId" }
        var typedQuery = entityManager.createQuery(query).setParameter("id", id)
        userId?.let { typedQuery = typedQuery.setParameter("userId", userId) }
        return typedQuery.executeUpdate() > 0
    }

    override fun count(criteria: DayPlanCriteria, userId: Int?): Long {
        return applyParameters(
            criteria,
            entityManager.createQuery(
                "SELECT DISTINCT COUNT(dp) FROM DayPlan dp WHERE dp.userId = :userId" + prepareCriteria(
                    criteria,
                    userId
                ),
                Long::class.javaObjectType
            ), userId
        ).singleResult
    }

    override fun findById(id: Int, userId: Int): DayPlan? {
        return entityManager
            .createQuery(
                "SELECT dp FROM DayPlan dp WHERE dp.id = :id AND dp.userId = :userId",
                DayPlan::class.java
            )
            .setParameter("id", id)
            .setParameter("userId", userId)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    override fun findByDate(date: LocalDate, userId: Int): DayPlan? {
        return entityManager
            .createQuery(
                "SELECT dp FROM DayPlan dp WHERE dp.date = :date AND dp.userId = :userId",
                DayPlan::class.java
            )
            .setParameter("date", date)
            .setParameter("userId", userId)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    private fun prepareCriteria(criteria: DayPlanCriteria, userId: Int?): String {
        val builder = StringBuilder().append("")
        userId?.let { builder.append(" AND dp.userId = :userId") }
        criteria.since?.let { builder.append(" AND dp.date IS NOT NULL AND dp.date >= :since") }
        criteria.to?.let { builder.append(" AND dp.date IS NOT NULL AND dp.date <= :to") }
        return builder.toString()
    }

    private fun <T> applyParameters(
        criteria: DayPlanCriteria,
        typedQuery: TypedQuery<T>,
        userId: Int?
    ): TypedQuery<T> {
        var query = typedQuery
        userId?.let { query = typedQuery.setParameter("userId", it) }
        criteria.since?.let { query = typedQuery.setParameter("since", it) }
        criteria.to?.let { query = typedQuery.setParameter("to", criteria.to) }
        return query
    }

}