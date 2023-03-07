package pl.szczeliniak.kitchenassistant

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import java.util.*

@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
class RequestContextImpl : RequestContext {

    private var requestId: UUID? = null
    private var userId: Int? = null

    override fun userId(userId: Int?) {
        this.userId = userId
    }

    override fun userId(): Int? {
        return userId
    }

    override fun requestId(requestId: UUID?) {
        this.requestId = requestId
    }

    override fun requestId(): UUID? {
        return requestId
    }

}