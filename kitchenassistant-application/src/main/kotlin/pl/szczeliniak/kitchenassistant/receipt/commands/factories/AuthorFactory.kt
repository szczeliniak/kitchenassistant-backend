package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.receipt.Author

open class AuthorFactory {

    open fun create(name: String, userId: Int): Author {
        return Author(name_ = name, userId_ = userId)
    }

}