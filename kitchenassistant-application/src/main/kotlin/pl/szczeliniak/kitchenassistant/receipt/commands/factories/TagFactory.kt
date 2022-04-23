package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.receipt.Tag

open class TagFactory {

    open fun create(name: String, userId: Int): Tag {
        return Tag(name_ = name, userId_ = userId)
    }

}