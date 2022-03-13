package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository : JpaRepository<IngredientEntity, Int>