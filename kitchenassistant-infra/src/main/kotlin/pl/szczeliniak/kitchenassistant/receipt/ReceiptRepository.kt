package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class ReceiptRepository(@PersistenceContext private val entityManager: EntityManager) : ReceiptDao {

    override fun findAll(criteria: ReceiptCriteria, offset: Int?, limit: Int?): Set<Receipt> {
        val query =
            "SELECT DISTINCT r FROM Receipt r " + prepareJoin(criteria) + "WHERE r.deleted = false" + prepareCriteria(
                criteria
            ) + " ORDER BY r.favorite DESC NULLS LAST, r.id ASC"
        val typedQuery = applyParameters(criteria, entityManager.createQuery(query, Receipt::class.java))
        offset?.let { typedQuery.firstResult = it }
        limit?.let { typedQuery.maxResults = it }
        return typedQuery.resultList.toMutableSet()
    }

    private fun prepareJoin(criteria: ReceiptCriteria): String {
        val builder = StringBuilder()
        if (criteria.tag != null) {
            builder.append("JOIN r.tags t ")
        }
        return builder.toString()
    }

    override fun count(criteria: ReceiptCriteria): Long {
        val query =
            "SELECT DISTINCT COUNT(r) FROM Receipt r " + prepareJoin(criteria) + "WHERE r.deleted = false" + prepareCriteria(
                criteria
            )
        return applyParameters(criteria, entityManager.createQuery(query, Long::class.javaObjectType)).singleResult
    }

    override fun findById(id: Int): Receipt? {
        return entityManager
            .createQuery(
                "SELECT r FROM Receipt r WHERE r.id = :id AND r.deleted = false",
                Receipt::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    @Transactional
    override fun save(receipt: Receipt): Receipt {
        if (receipt.id == 0) {
            entityManager.persist(receipt)
        } else {
            entityManager.merge(receipt)
        }
        return receipt
    }

    override fun save(receipts: Set<Receipt>) {
        receipts.forEach { save(it) }
    }

    private fun prepareCriteria(criteria: ReceiptCriteria): String {
        val builder = StringBuilder().append("")
        if (criteria.userId != null) {
            builder.append(" AND r.userId = :userId")
        }
        if (criteria.categoryId != null) {
            builder.append(" AND r.category.id = :categoryId")
        }
        if (criteria.name != null) {
            builder.append(" AND LOWER(r.name) LIKE LOWER(:name)")
        }
        if (criteria.tag != null) {
            builder.append(" AND LOWER(t.name) LIKE LOWER(:tag)")
        }
        return builder.toString()
    }

    private fun <T> applyParameters(
        criteria: ReceiptCriteria,
        typedQuery: TypedQuery<T>
    ): TypedQuery<T> {
        var query = typedQuery
        if (criteria.userId != null) {
            query = typedQuery.setParameter("userId", criteria.userId)
        }
        if (criteria.categoryId != null) {
            query = typedQuery.setParameter("categoryId", criteria.categoryId)
        }
        if (criteria.name != null) {
            query = typedQuery.setParameter("name", "%" + criteria.name + "%")
        }
        if (criteria.tag != null) {
            query = typedQuery.setParameter("tag", "%" + criteria.tag + "%")
        }
        return query
    }

}