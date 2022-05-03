package pl.szczeliniak.kitchenassistant.receipt

import pl.szczeliniak.kitchenassistant.shared.exceptions.NotAllowedOperationException
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import java.time.LocalDateTime
import java.util.*

data class Step(
    private var id_: Int = 0,
    private var name_: String,
    private var description_: String?,
    private var sequence_: Int?,
    private var photos_: MutableSet<Photo> = mutableSetOf(),
    private var deleted_: Boolean = false,
    private val createdAt_: LocalDateTime = LocalDateTime.now(),
    private var modifiedAt_: LocalDateTime = LocalDateTime.now()
) {
    val id: Int get() = id_
    val name: String get() = name_
    val description: String? get() = description_
    val sequence: Int? get() = sequence_
    val photos: Set<Photo> get() = Collections.unmodifiableSet(photos_)
    val createdAt: LocalDateTime get() = createdAt_
    val modifiedAt: LocalDateTime get() = modifiedAt_
    val deleted: Boolean get() = deleted_

    fun markAsDeleted() {
        if (deleted) {
            throw NotAllowedOperationException("Step is already marked as deleted!")
        }
        deleted_ = true
        this.modifiedAt_ = LocalDateTime.now()
    }

    fun update(name: String, description: String?, sequence: Int?) {
        this.name_ = name
        this.description_ = description
        this.sequence_ = sequence
        this.modifiedAt_ = LocalDateTime.now()
    }

    fun deletePhotoById(id: Int): Photo {
        val photo = photos_.firstOrNull { it.id == id } ?: throw NotFoundException("Photo not found")
        photos_.remove(photo)
        return photo
    }

    fun addPhoto(photo: Photo) {
        photos_ += photo
        modifiedAt_ = LocalDateTime.now()
    }

}
