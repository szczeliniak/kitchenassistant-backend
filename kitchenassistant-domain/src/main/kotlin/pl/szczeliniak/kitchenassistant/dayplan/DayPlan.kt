package pl.szczeliniak.kitchenassistant.dayplan

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*

data class DayPlan(
    private var id_: Int = 0,
    private var userId_: Int,
    private var date_: LocalDate,
    private var receiptIds_: MutableSet<Int> = mutableSetOf(),
    private var deleted_: Boolean = false,
    private var archived_: Boolean = false,
    private val createdAt_: ZonedDateTime = ZonedDateTime.now(),
    private var modifiedAt_: ZonedDateTime = ZonedDateTime.now()
) {

    val id: Int get() = id_
    val userId: Int get() = userId_
    val date: LocalDate get() = date_
    val receiptIds: Set<Int> get() = Collections.unmodifiableSet(receiptIds_)
    val createdAt: ZonedDateTime get() = createdAt_
    val modifiedAt: ZonedDateTime get() = modifiedAt_
    val deleted: Boolean get() = deleted_
    val archived: Boolean get() = archived_

    fun delete() {
        if (deleted_) {
            throw KitchenAssistantException(ErrorCode.DAY_PLAN_ALREADY_REMOVED)
        }
        deleted_ = true
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun update(date: LocalDate) {
        this.date_ = date
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun addReceiptId(receiptId: Int) {
        this.receiptIds_.add(receiptId)
    }

    fun archive() {
        if (archived_) {
            throw KitchenAssistantException(ErrorCode.DAY_PLAN_ALREADY_ARCHIVED)
        }
        archived_ = true
        this.modifiedAt_ = ZonedDateTime.now()
    }

    fun removeReceiptId(receiptId: Int) {
        if (!this.receiptIds_.contains(receiptId)) {
            throw KitchenAssistantException(ErrorCode.RECEIPT_ID_IS_NOT_ASSIGNED_TO_DAY_PLAN)
        }
        this.receiptIds_.remove(receiptId)
    }

}