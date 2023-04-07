package pl.szczeliniak.kitchenassistant.recipe.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PhotoRepository : JpaRepository<Photo, Int> {

    @Query("SELECT r FROM Photo r WHERE r.id = :id AND r.deleted = false")
    override fun findById(id: Int): Optional<Photo>

    @Query("SELECT p.* FROM photos p LEFT JOIN recipes_photos rp ON rp.photo_id = p.id WHERE rp.recipe_id IS NULL AND p.deleted = false", nativeQuery = true)
    fun findOrphaned(): Set<Photo>

    @Query("SELECT p.* FROM photos p INNER JOIN recipes_photos rp ON rp.photo_id = p.id WHERE p.deleted = false", nativeQuery = true)
    fun findAssigned(): Set<Photo>

}