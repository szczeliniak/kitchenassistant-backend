package pl.szczeliniak.kitchenassistant.files

interface FtpClient {

    fun upload(name: String, content: ByteArray): String

    fun download(name: String): ByteArray

    fun delete(name: String)

}