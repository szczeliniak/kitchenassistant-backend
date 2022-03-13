package pl.szczeliniak.kitchenassistant

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.stubbing.OngoingStubbing

@ExtendWith(MockitoExtension::class)
open class JunitBaseClass : WithAssertions {

    fun <T> whenever(methodCall: T): OngoingStubbing<T> =
        Mockito.`when`(methodCall)

}