package pl.szczeliniak.kitchenassistant

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class KitchenAssistantApplication

fun main(args: Array<String>) {
    runApplication<KitchenAssistantApplication>(*args)
}