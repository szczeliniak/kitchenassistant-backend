package pl.szczeliniak.kitchenassistant.user.commands.factories

interface TokenFactory {

    fun create(userId: Int): String

}