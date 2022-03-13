package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.springframework.data.jpa.repository.JpaRepository

interface ReceiptRepository : JpaRepository<ReceiptEntity, Int>