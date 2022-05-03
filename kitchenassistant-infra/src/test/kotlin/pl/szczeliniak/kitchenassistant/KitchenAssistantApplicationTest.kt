package pl.szczeliniak.kitchenassistant

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class KitchenAssistantApplicationTest

fun main(args: Array<String>) {
    runApplication<KitchenAssistantApplicationTest>(*args)
}