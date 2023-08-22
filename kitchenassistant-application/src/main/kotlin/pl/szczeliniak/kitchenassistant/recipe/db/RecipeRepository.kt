package pl.szczeliniak.kitchenassistant.recipe.db

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class RecipeRepository(@PersistenceContext private val entityManager: EntityManager) : RecipeDao {

    override fun findAll(criteria: RecipeCriteria, offset: Int?, limit: Int?): Set<Recipe> {
        val query =
            "SELECT DISTINCT r FROM Recipe r " + prepareJoin(criteria) + "WHERE r.id IS NOT NULL" + prepareCriteria(
                criteria
            ) + " ORDER BY r.id ASC"
        val typedQuery = applyParameters(criteria, entityManager.createQuery(query, Recipe::class.java))
        offset?.let { typedQuery.firstResult = it }
        limit?.let { typedQuery.maxResults = it }
        return typedQuery.resultList.toMutableSet()
    }

    private fun prepareJoin(criteria: RecipeCriteria): String {
        val builder = StringBuilder()
        if (criteria.tag != null) {
            builder.append("JOIN r.tags t ")
        }
        return builder.toString()
    }

    override fun count(criteria: RecipeCriteria): Long {
        val query =
            "SELECT DISTINCT COUNT(r) FROM Recipe r " + prepareJoin(criteria) + "WHERE r.id IS NOT NULL" + prepareCriteria(
                criteria
            )
        return applyParameters(criteria, entityManager.createQuery(query, Long::class.javaObjectType)).singleResult
    }

    @Transactional
    override fun delete(recipe: Recipe) {
        entityManager.remove(recipe)
    }

    override fun findById(id: Int): Recipe? {
        return entityManager
            .createQuery(
                "SELECT r FROM Recipe r WHERE r.id = :id",
                Recipe::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    @Transactional
    override fun save(recipe: Recipe): Recipe {
        if (recipe.id == 0) {
            entityManager.persist(recipe)
        } else {
            entityManager.merge(recipe)
        }
        return recipe
    }

    override fun save(recipes: Set<Recipe>) {
        recipes.forEach { save(it) }
    }

    private fun prepareCriteria(criteria: RecipeCriteria): String {
        val builder = StringBuilder().append("")
        if (criteria.userId != null) {
            builder.append(" AND r.user.id = :userId")
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
        if (criteria.onlyFavorites != null && criteria.onlyFavorites!!) {
            builder.append(" AND r.favorite = :favorite")
        }
        if (criteria.fileName != null) {
            builder.append(" AND r.photoName = :photoName")
        }
        return builder.toString()
    }

    private fun <T> applyParameters(
        criteria: RecipeCriteria,
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
        if (criteria.onlyFavorites != null && criteria.onlyFavorites!!) {
            query = typedQuery.setParameter("favorite", true)
        }
        if (criteria.fileName != null) {
            query = typedQuery.setParameter("photoName", criteria.fileName)
        }
        return query
    }

}