package pl.szczeliniak.kitchenassistant.file.commands.factories

import pl.szczeliniak.kitchenassistant.file.File

open class FileFactory {

    open fun create(name: String, userId: Int): File {
        return File(name_ = name, userId_ = userId)
    }

}