package pl.szczeliniak.cookbook

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import pl.szczeliniak.cookbook.shared.CookBookException
import pl.szczeliniak.cookbook.shared.ErrorCode
import pl.szczeliniak.cookbook.shared.RequestContext
import pl.szczeliniak.cookbook.shared.TokenType
import java.util.*

@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
class RequestContextImpl : RequestContext {

    private var requestId: UUID? = null
    private var userId: Int? = null
    private var tokenType: TokenType? = null
    override fun userId(userId: Int?) {
        this.userId = userId
    }

    override fun requestId(requestId: UUID?) {
        this.requestId = requestId
    }

    override fun tokenType(tokenType: TokenType) {
        this.tokenType = tokenType;
    }

    override fun requestId(): UUID {
        return requestId ?: throw CookBookException(ErrorCode.MISSING_REQUEST_ID)
    }

    override fun userId(): Int {
        return this.userId ?: throw CookBookException(ErrorCode.MISSING_USER_ID)
    }

    override fun tokenType(): TokenType {
        return this.tokenType ?: throw CookBookException(ErrorCode.MISSING_TOKEN_TYPE)
    }

}