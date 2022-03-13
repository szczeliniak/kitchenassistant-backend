package pl.szczeliniak.kitchenassistant.shoppinglist.persistance

import org.springframework.data.jpa.repository.JpaRepository

interface ShoppingListRepository : JpaRepository<ShoppingListEntity, Int>