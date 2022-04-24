package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.receipt.Photo

open class PhotoFactory {

    open fun create(fileId: Int): Photo {
        return Photo(fileId_ = fileId)
    }

}