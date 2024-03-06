package pl.szczeliniak.kitchenassistant.dayplan.db

import org.springframework.stereotype.Repository
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
    override fun save(dayPlans: List<DayPlan>) {
        dayPlans.forEach { save(it) }
    }

    override fun findAll(criteria: DayPlanCriteria, userId: Int, sort: Sort, offset: Int?, limit: Int?): List<DayPlan> {
        val typedQuery = applyParameters(
            criteria,
            entityManager.createQuery(
                "SELECT dp FROM DayPlan dp WHERE dp.userId = :userId" + prepareCriteria(criteria) + " ORDER BY dp.date " + sort.name + ", dp.id " + sort.name,
                DayPlan::class.java
            ), userId
        )
        offset?.let { typedQuery.firstResult = it }
        limit?.let { typedQuery.maxResults = it }

        return typedQuery.resultList.toMutableList()
    }

    @Transactional
    override fun delete(dayPlan: DayPlan) {
        entityManager.remove(dayPlan)
    }

    override fun count(criteria: DayPlanCriteria, userId: Int): Long {
        return applyParameters(
            criteria,
            entityManager.createQuery(
                "SELECT DISTINCT COUNT(dp) FROM DayPlan dp WHERE dp.userId = :userId" + prepareCriteria(criteria),
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

    private fun prepareCriteria(criteria: DayPlanCriteria): String {
        val builder = StringBuilder().append("")
        criteria.since?.let { builder.append(" AND dp.date IS NOT NULL AND dp.date >= :since") }
        criteria.to?.let { builder.append(" AND dp.date IS NOT NULL AND dp.date <= :to") }
        return builder.toString()
    }

    private fun <T> applyParameters(
        criteria: DayPlanCriteria,
        typedQuery: TypedQuery<T>,
        userId: Int
    ): TypedQuery<T> {
        var query = typedQuery.setParameter("userId", userId)
        criteria.since?.let { query = typedQuery.setParameter("since", it) }
        criteria.to?.let { query = typedQuery.setParameter("to", criteria.to) }
        return query
    }

}