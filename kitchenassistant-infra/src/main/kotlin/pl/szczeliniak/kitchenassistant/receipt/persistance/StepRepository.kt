package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.springframework.data.jpa.repository.JpaRepository

interface StepRepository : JpaRepository<StepEntity, Int>