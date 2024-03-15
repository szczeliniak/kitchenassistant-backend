package pl.szczeliniak.kitchenassistant.user

interface MailService {

    fun send(receiver: String, title: String, content: String)

}