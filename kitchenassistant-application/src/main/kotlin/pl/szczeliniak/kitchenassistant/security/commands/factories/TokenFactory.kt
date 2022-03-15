package pl.szczeliniak.kitchenassistant.security.commands.factories

interface TokenFactory {

    fun create(userId: Int): String

}