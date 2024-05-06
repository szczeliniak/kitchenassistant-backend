package pl.szczeliniak.cookbook.recipe.db

import org.springframework.stereotype.Repository
import java.time.ZonedDateTime
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class RecipeRepository(@PersistenceContext private val entityManager: EntityManager) : RecipeDao {

    override fun findAll(criteria: RecipeCriteria, userId: Int, offset: Int?, limit: Int?): List<Recipe> {
        val query =
            "SELECT DISTINCT r FROM Recipe r " + prepareJoin(criteria) + "WHERE r.id IS NOT NULL" + prepareCriteria(
                criteria
            ) + " ORDER BY r.favorite DESC, r.id ASC"
        val typedQuery = applyParameters(criteria, userId, entityManager.createQuery(query, Recipe::class.java))
        offset?.let { typedQuery.firstResult = it }
        limit?.let { typedQuery.maxResults = it }
        return typedQuery.resultList.toMutableList()
    }

    private fun prepareJoin(criteria: RecipeCriteria): String {
        val builder = StringBuilder()
        if (criteria.tag != null) {
            builder.append("JOIN r.tags t ")
        }
        if (criteria.search != null) {
            builder.append("JOIN r.author a ")
        }
        return builder.toString()
    }

    override fun count(criteria: RecipeCriteria, userId: Int): Long {
        val query =
            "SELECT DISTINCT COUNT(r) FROM Recipe r " + prepareJoin(criteria) + "WHERE r.id IS NOT NULL" + prepareCriteria(
                criteria
            )
        return applyParameters(criteria, userId, entityManager.createQuery(query, Long::class.javaObjectType)).singleResult
    }

    override fun findById(id: Int, archived: Boolean?, userId: Int): Recipe? {
        var query = "SELECT r FROM Recipe r WHERE r.id = :id AND r.user.id = :userId"
        archived?.let {
            query = "$query AND r.archived = :archived"
        }
        var typedQuery = entityManager.createQuery(query, Recipe::class.java)
            .setParameter("id", id)
            .setParameter("userId", userId)

        archived?.let {
            typedQuery = typedQuery.setParameter("archived", archived)
        }

        return typedQuery.resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    @Transactional
    override fun save(recipe: Recipe): Recipe {
        if (recipe.id == 0) {
            entityManager.persist(recipe)
        } else {
            recipe.modifiedAt = ZonedDateTime.now()
            entityManager.merge(recipe)
        }
        return recipe
    }

    override fun save(recipes: List<Recipe>) {
        recipes.forEach { save(it) }
    }

    private fun prepareCriteria(criteria: RecipeCriteria): String {
        val builder = StringBuilder().append(" AND r.user.id = :userId")
        if (criteria.categoryId != null) {
            builder.append(" AND r.category.id = :categoryId")
        }
        if (criteria.search != null) {
            builder.append(" AND (LOWER(r.name) LIKE LOWER(:search) OR LOWER(a.name) LIKE LOWER(:search) OR LOWER(r.category.name) LIKE LOWER(:search))")
        }
        if (criteria.tag != null) {
            builder.append(" AND LOWER(t.name) LIKE LOWER(:tag)")
        }
        if (criteria.favorite != null) {
            builder.append(" AND r.favorite = :favorite")
        }
        if (criteria.archived != null) {
            builder.append(" AND r.archived = :archived")
        }
        return builder.toString()
    }

    private fun <T> applyParameters(
        criteria: RecipeCriteria,
        userId: Int,
        typedQuery: TypedQuery<T>
    ): TypedQuery<T> {
        var query = typedQuery.setParameter("userId", userId)
        if (criteria.categoryId != null) {
            query = typedQuery.setParameter("categoryId", criteria.categoryId)
        }
        if (criteria.search != null) {
            query = typedQuery.setParameter("search", "%" + criteria.search + "%")
        }
        if (criteria.tag != null) {
            query = typedQuery.setParameter("tag", "%" + criteria.tag + "%")
        }
        if (criteria.favorite != null) {
            query = typedQuery.setParameter("favorite", criteria.favorite)
        }
        if (criteria.archived != null) {
            query = typedQuery.setParameter("archived", criteria.archived)
        }
        return query
    }

}