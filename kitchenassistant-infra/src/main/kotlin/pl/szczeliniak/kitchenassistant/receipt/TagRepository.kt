package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class TagRepository(@PersistenceContext private val entityManager: EntityManager) : TagDao {

    @Transactional
    override fun save(tag: Tag): Tag {
        if (tag.id == 0) {
            entityManager.persist(tag)
        } else {
            entityManager.merge(tag)
        }
        return tag
    }

    override fun saveAll(tags: Set<Tag>) {
        TODO("Not yet implemented")
    }

    override fun findById(id: Int): Tag? {
        return entityManager
            .createQuery(
                "SELECT t FROM Tag t WHERE t.id = :id",
                Tag::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    override fun findByName(name: String, userId: Int): Tag? {
        return entityManager
            .createQuery(
                "SELECT t FROM Tag t WHERE t.name = :name AND t.userId = :userId",
                Tag::class.java
            )
            .setParameter("name", name)
            .setParameter("userId", userId)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    override fun findAll(criteria: TagCriteria): Set<Tag> {
        var query = "SELECT t FROM Tag t WHERE t.id IS NOT NULL"
        if (criteria.name != null) {
            query += " AND LOWER(t.name) LIKE (:name)"
        }
        if (criteria.userId != null) {
            query += " AND t.userId = :userId"
        }

        var typedQuery = entityManager.createQuery(query, Tag::class.java)
        if (criteria.name != null) {
            typedQuery = typedQuery.setParameter("name", "%" + criteria.name + "%")
        }
        if (criteria.userId != null) {
            typedQuery = typedQuery.setParameter("userId", criteria.userId)
        }

        return typedQuery.resultList.toMutableSet()
    }

}