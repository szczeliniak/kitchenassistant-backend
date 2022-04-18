package pl.szczeliniak.kitchenassistant.file

interface FtpClient {

    fun upload(name: String, content: ByteArray): String

    fun download(name: String): ByteArray

    fun delete(name: String)

}