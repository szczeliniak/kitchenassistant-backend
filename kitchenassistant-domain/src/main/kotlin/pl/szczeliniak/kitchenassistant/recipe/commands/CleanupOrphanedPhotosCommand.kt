package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.db.PhotoDao

class CleanupOrphanedPhotosCommand(private val ftpClient: FtpClient, private val photoDao: PhotoDao) {

    fun execute() {
        photoDao.findOrphaned().forEach {
            ftpClient.delete(it.name)
            it.deleted = true
            photoDao.save(it)
        }
    }

}