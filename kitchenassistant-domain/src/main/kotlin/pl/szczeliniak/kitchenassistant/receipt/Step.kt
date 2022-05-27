package pl.szczeliniak.kitchenassistant.receipt

import pl.szczeliniak.kitchenassistant.shared.exceptions.NotAllowedOperationException
import java.time.ZonedDateTime

data class Step(
    private var id_: Int = 0,
    private var name_: String,
    private var description_: String?,
    private var sequence_: Int?,
    private var deleted_: Boolean = false,
    private val createdAt_: ZonedDateTime = ZonedDateTime.now(),
    private var modifiedAt_: ZonedDateTime = ZonedDateTime.now()
) {
    val id: Int get() = id_
    val name: String get() = name_
    val description: String? get() = description_
    val sequence: Int? get() = sequence_
    val createdAt: ZonedDateTime get() = createdAt_
    val modifiedAt: ZonedDateTime get() = modifiedAt_
    val deleted: Boolean get() = deleted_

    fun markAsDeleted() {
        if (deleted) {
            throw NotAllowedOperationException("Step is already marked as deleted!")
        }
        deleted_ = true
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun update(name: String, description: String?, sequence: Int?) {
        this.name_ = name
        this.description_ = description
        this.sequence_ = sequence
        this.modifiedAt_ = ZonedDateTime.now()
    }

}