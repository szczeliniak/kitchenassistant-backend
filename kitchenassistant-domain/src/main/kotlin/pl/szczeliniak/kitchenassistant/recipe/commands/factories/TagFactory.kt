package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.db.Tag

open class TagFactory {

    open fun create(name: String, userId: Int): Tag {
        return Tag(0, name, userId)
    }

}