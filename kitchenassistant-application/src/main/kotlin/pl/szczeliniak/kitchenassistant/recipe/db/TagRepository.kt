package pl.szczeliniak.kitchenassistant.recipe.db

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

    override fun findById(id: Int, userId: Int): Tag? {
        return entityManager
            .createQuery(
                "SELECT t FROM Tag t WHERE t.id = :id AND t.user.id = :userId",
                Tag::class.java
            )
            .setParameter("id", id)
            .setParameter("userId", userId)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    override fun findByName(name: String, userId: Int): Tag? {
        return entityManager
            .createQuery(
                "SELECT t FROM Tag t WHERE t.name = :name AND t.user.id = :userId",
                Tag::class.java
            )
            .setParameter("name", name)
            .setParameter("userId", userId)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    override fun findAll(criteria: TagCriteria, userId: Int): Set<Tag> {
        var query = "SELECT t FROM Tag t WHERE t.user.id = :userId"
        if (criteria.name != null) {
            query += " AND LOWER(t.name) LIKE (:name)"
        }
        var typedQuery = entityManager.createQuery(query, Tag::class.java).setParameter("userId", userId)
        if (criteria.name != null) {
            typedQuery = typedQuery.setParameter("name", "%" + criteria.name + "%")
        }
        return typedQuery.resultList.toMutableSet()
    }

}