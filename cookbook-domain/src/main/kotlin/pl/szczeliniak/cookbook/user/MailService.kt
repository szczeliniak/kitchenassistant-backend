package pl.szczeliniak.cookbook.user

interface MailService {

    fun send(receiver: String, title: String, content: String)

}