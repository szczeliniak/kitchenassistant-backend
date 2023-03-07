package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.db.Author

open class AuthorFactory {

    open fun create(name: String, userId: Int): Author {
        return Author(0, name, userId)
    }

}