package pl.szczeliniak.kitchenassistant.shoppinglist

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ShoppingListScheduler(private val shoppingListFacade: ShoppingListFacade) {

    private val logger: Logger = LoggerFactory.getLogger(ShoppingListScheduler::class.java)

    @Transactional
    @Scheduled(cron = "\${scheduling.shoppinglist.archiving}")
    fun terminateShoppingLists() {
        logger.info("Shopping list termination has been triggered!")
        shoppingListFacade.triggerArchiving()
        logger.info("Shopping list termination has been finished!")
    }

}