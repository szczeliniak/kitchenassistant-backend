package pl.szczeliniak.kitchenassistant

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KitchenAssistantApplicationTest

fun main(args: Array<String>) {
    runApplication<KitchenAssistantApplication>(*args)
}