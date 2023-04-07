package pl.szczeliniak.kitchenassistant.recipe

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecipeOrphanPhotosCleanUpScheduler(private val recipeFacade: RecipeFacade) {

    private val logger: Logger = LoggerFactory.getLogger(RecipeOrphanPhotosCleanUpScheduler::class.java)

    @Transactional
    @Scheduled(cron = "\${scheduling.recipe.photo.orphans.cleanup}")
    fun cleanUpOrphanedPhotosForRecipes() {
        logger.info("Cleaning orphaned photos for recipes has been triggered!")
        recipeFacade.cleanupOrphanedPhotos()
        logger.info("Cleaning orphaned photos for recipes has been finished!")
    }

}