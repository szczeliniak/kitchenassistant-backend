package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.Photo

open class PhotoFactory {

    open fun create(name: String, userId: Int): Photo {
        return Photo(0, name, userId)
    }

}