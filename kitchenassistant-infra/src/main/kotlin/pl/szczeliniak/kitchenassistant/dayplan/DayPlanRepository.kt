package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class DayPlanRepository(@PersistenceContext private val entityManager: EntityManager) {

    @Transactional
    fun save(entity: DayPlanEntity): DayPlanEntity {
        if (entity.id == 0) {
            entityManager.persist(entity)
        } else {
            entityManager.merge(entity)
        }
        return entity
    }

    fun findAll(offset: Int?, limit: Int?, criteria: SearchCriteria): MutableSet<DayPlanEntity> {
        val typedQuery = applyParameters(
            criteria,
            entityManager.createQuery(
                "SELECT dp FROM DayPlanEntity dp WHERE dp.deleted = false" + prepareCriteria(criteria) + " ORDER BY dp.date DESC, dp.id ASC",
                DayPlanEntity::class.java
            )
        )
        offset?.let { typedQuery.firstResult = it }
        limit?.let { typedQuery.maxResults = it }
        return typedQuery.resultList.toMutableSet()
    }

    fun findById(id: Int): DayPlanEntity? {
        return entityManager
            .createQuery(
                "SELECT dp FROM DayPlanEntity dp WHERE dp.id = :id AND dp.deleted = false",
                DayPlanEntity::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    @Transactional
    fun clear() {
        entityManager.createQuery("DELETE FROM DayPlanEntity").executeUpdate()
    }

    fun count(criteria: SearchCriteria): Long {
        return applyParameters(
            criteria,
            entityManager.createQuery(
                "SELECT DISTINCT COUNT(dp) FROM DayPlanEntity dp WHERE dp.deleted = false" + prepareCriteria(criteria),
                Long::class.javaObjectType
            )
        ).singleResult
    }

    private fun prepareCriteria(criteria: SearchCriteria): String {
        val builder = StringBuilder().append("")
        if (criteria.userId != null) {
            builder.append(" AND dp.userId = :userId")
        }
        if (criteria.archived != null) {
            builder.append(" AND dp.archived = :archived")
        }
        return builder.toString()
    }

    private fun <T> applyParameters(
        criteria: SearchCriteria,
        typedQuery: TypedQuery<T>
    ): TypedQuery<T> {
        var query = typedQuery
        if (criteria.userId != null) {
            query = typedQuery.setParameter("userId", criteria.userId)
        }
        if (criteria.archived != null) {
            query = typedQuery.setParameter("archived", criteria.archived)
        }
        return query
    }

    data class SearchCriteria(val userId: Int?, val archived: Boolean?)

}