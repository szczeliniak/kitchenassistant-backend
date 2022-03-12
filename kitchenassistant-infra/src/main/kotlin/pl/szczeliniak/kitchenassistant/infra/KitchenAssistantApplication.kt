package pl.szczeliniak.kitchenassistant.infra

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KitchenAssistantApplication

fun main(args: Array<String>) {
    runApplication<KitchenAssistantApplication>(*args)
}
