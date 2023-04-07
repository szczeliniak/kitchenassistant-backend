package pl.szczeliniak.kitchenassistant.recipe

interface FtpClient {

    fun upload(name: String, content: ByteArray): String

    fun download(name: String): ByteArray

    fun delete(name: String)

    fun exists(name: String): Boolean

}