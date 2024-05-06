package pl.szczeliniak.cookbook

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpRequest
import org.springframework.http.client.*
import org.springframework.util.CollectionUtils
import org.springframework.web.client.RestTemplate
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

@Configuration
class RestTemplateConfiguration {

    @Bean
    fun restTemplate(): RestTemplate? {
        val restTemplate = RestTemplate(BufferingClientHttpRequestFactory(SimpleClientHttpRequestFactory()))
        var interceptors = restTemplate.interceptors
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = ArrayList()
        }
        interceptors.add(RestTemplateLogInterceptor())
        restTemplate.interceptors = interceptors
        return restTemplate
    }

    inner class RestTemplateLogInterceptor : ClientHttpRequestInterceptor {

        private val logger: Logger = LoggerFactory.getLogger(RestTemplateLogInterceptor::class.java)

        override fun intercept(
            request: HttpRequest,
            body: ByteArray,
            execution: ClientHttpRequestExecution
        ): ClientHttpResponse {
            traceRequest(request, body)
            val response = execution.execute(request, body)
            traceResponse(response)
            return response
        }

        private fun traceRequest(request: HttpRequest, body: ByteArray) {
            logger.info("===========================request begin================================================")
            logger.info("URI         : {}", request.uri)
            logger.info("Method      : {}", request.method)
            logger.info("Headers     : {}", request.headers)
            logger.info("Request body: {}", String(body, StandardCharsets.UTF_8))
            logger.info("==========================request end================================================")
        }

        private fun traceResponse(response: ClientHttpResponse) {
            val inputStringBuilder = StringBuilder()
            val bufferedReader = BufferedReader(InputStreamReader(response.body, StandardCharsets.UTF_8))
            var line = bufferedReader.readLine()
            while (line != null) {
                inputStringBuilder.append(line)
                inputStringBuilder.append('\n')
                line = bufferedReader.readLine()
            }
            logger.info("============================response begin==========================================")
            logger.info("Status code  : {}", response.statusCode)
            logger.info("Status text  : {}", response.statusText)
            logger.info("Headers      : {}", response.headers)
            logger.info("Response body: {}", inputStringBuilder)
            logger.info("=======================response end=================================================")
        }

    }

}