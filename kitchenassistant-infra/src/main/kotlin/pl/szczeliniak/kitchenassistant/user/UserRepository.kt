package pl.szczeliniak.kitchenassistant.user

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class UserRepository(@PersistenceContext private val entityManager: EntityManager) : UserDao {

    override fun findAll(criteria: UserCriteria, offset: Int, limit: Int): Set<User> {
        val query = "SELECT u FROM User u" + prepareCriteria(criteria)
        val typedQuery = applyParameters(criteria, entityManager.createQuery(query, User::class.java))
        typedQuery.firstResult = offset
        typedQuery.maxResults = limit
        return typedQuery.resultList.toSet()
    }

    override fun count(criteria: UserCriteria): Long {
        val query = "SELECT COUNT(u) FROM User u" + prepareCriteria(criteria)
        return applyParameters(criteria, entityManager.createQuery(query, Long::class.javaObjectType)).singleResult
    }

    private fun prepareCriteria(criteria: UserCriteria): String {
        val builder = StringBuilder().append("")
        if (criteria.email != null) {
            builder.append(" WHERE u.email = :email")
        }
        return builder.toString()
    }

    override fun findById(id: Int): User? {
        return entityManager
            .createQuery("SELECT u FROM User u WHERE u.id = :id", User::class.java)
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    @Transactional
    override fun save(user: User): User {
        if (user.id == 0) {
            entityManager.persist(user)
        } else {
            entityManager.merge(user)
        }
        return user
    }

    private fun <T> applyParameters(
        criteria: UserCriteria,
        typedQuery: TypedQuery<T>
    ): TypedQuery<T> {
        var query = typedQuery
        if (criteria.email != null) {
            query = typedQuery.setParameter("email", criteria.email)
        }
        return query
    }

}