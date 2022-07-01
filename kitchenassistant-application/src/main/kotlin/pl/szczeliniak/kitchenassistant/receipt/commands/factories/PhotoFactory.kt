package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.receipt.Photo

open class PhotoFactory {

    open fun create(name: String, userId: Int): Photo {
        return Photo(0, name, userId)
    }

}