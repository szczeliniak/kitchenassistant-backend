package pl.szczeliniak.kitchenassistant.recipe.db

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class PhotoRepository(@PersistenceContext private val entityManager: EntityManager) : PhotoDao {

    @Transactional
    override fun save(photo: Photo): Photo {
        if (photo.id == 0) {
            entityManager.persist(photo)
        } else {
            entityManager.merge(photo)
        }
        return photo
    }

    override fun saveAll(photos: Set<Photo>) {
        photos.forEach { save(it) }
    }

    override fun findById(id: Int): Photo? {
        return entityManager
            .createQuery(
                "SELECT r FROM Photo r WHERE r.id = :id AND r.deleted = false",
                Photo::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

}