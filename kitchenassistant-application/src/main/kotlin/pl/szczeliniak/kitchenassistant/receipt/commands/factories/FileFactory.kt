package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.receipt.File

open class FileFactory {

    open fun create(name: String): File {
        return File(name_ = name)
    }

}