package pl.szczeliniak.kitchenassistant.user

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import javax.mail.internet.MimeMessage


@Component
class MailServiceImpl(
    val mailSender: JavaMailSender,
    @Value("\${spring.mail.username}") val sender: String,
    @Value("\${application.name}") val applicationName: String,
    @Value("\${spring.mail.enable}") val enabled: Boolean
) : MailService {

    private val logger: Logger = LoggerFactory.getLogger(JavaMailSender::class.java)

    override fun send(receiver: String, title: String, content: String) {
        if (enabled) {
            mailSender.send(buildMail(receiver, title, content))
        } else {
            logger.info("Sending email to $receiver with title $title and $content")
        }
    }

    private fun buildMail(receiver: String, title: String, content: String): MimeMessage {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, "utf-8")
        helper.setText(content, true)
        helper.setFrom(sender)
        helper.setTo(receiver)
        helper.setSubject("[$applicationName] $title")
        return message
    }

}