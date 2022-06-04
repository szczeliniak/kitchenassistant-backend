package pl.szczeliniak.kitchenassistant.receipt

interface FtpClient {

    fun upload(name: String, content: ByteArray): String

    fun download(name: String): ByteArray

    fun delete(name: String)

}