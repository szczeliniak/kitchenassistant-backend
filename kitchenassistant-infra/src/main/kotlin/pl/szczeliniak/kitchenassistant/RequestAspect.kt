package pl.szczeliniak.kitchenassistant

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Aspect
@Component
class RequestAspect(
    private val httpServletRequest: HttpServletRequest,
    private val httpServletResponse: HttpServletResponse,
    private val requestContext: RequestContext,
) {

    private val logger: Logger = LoggerFactory.getLogger(RequestAspect::class.java)

    @Pointcut(
        "@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
                "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
                "@annotation(org.springframework.web.bind.annotation.PutMapping) ||" +
                "@annotation(org.springframework.web.bind.annotation.DeleteMapping) ||" +
                "@annotation(org.springframework.web.bind.annotation.GetMapping)"
    )
    private fun anyRequest() {
    }

    @Around("anyRequest()")
    @Throws(Throwable::class)
    fun logAnyRequest(joinPoint: ProceedingJoinPoint): Any? {
        val requestId = UUID.randomUUID()
        requestContext.requestId(requestId)
        MDC.put("requestId", requestId.toString())

        logRequest(joinPoint.args)

        val proceed = joinPoint.proceed()

        logResponse(proceed)
        MDC.clear()
        return proceed
    }

    private fun logRequest(args: Array<Any>) {
        logger.info("--------------------------- REQUEST START----------------------------")
        logger.info("Request URI: " + httpServletRequest.requestURI)
        logger.info("Request method: " + httpServletRequest.method)
        logger.info("Request headers: " + getRequestHeaders())
        logger.info("Request arguments: " + listOf(*args))
        logger.info("Request ID: " + requestContext.requestId())
        logger.info("--------------------------- REQUEST FINISH----------------------------")
    }

    private fun getRequestHeaders(): Map<String, String> {
        val headers: MutableList<String> = mutableListOf()
        httpServletRequest.headerNames.asIterator().forEachRemaining { e: String -> headers.add(e) }
        return headers.distinct()
            .associateBy({ key: String -> key }, { s: String -> httpServletRequest.getHeader(s) })
    }

    private fun logResponse(proceed: Any) {
        logger.info("----------------------- RESPONSE START -----------------------")
        logger.info("Request URI: " + httpServletRequest.requestURI)
        logger.info("Request method: " + httpServletRequest.method)
        logger.info("Response headers: " + getResponseHeaders())
        logger.info("Response argument: $proceed")
        logger.info("Request ID: " + requestContext.requestId())
        logger.info("----------------------- RESPONSE FINISH -----------------------")
    }

    private fun getResponseHeaders(): Map<String, String> {
        return httpServletResponse.headerNames.distinct()
            .associateBy({ key: String -> key }, { s: String -> httpServletResponse.getHeader(s) })
    }

}