package pl.szczeliniak.kitchenassistant

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.annotation.PostConstruct


@Configuration
class JacksonConfiguration(private val objectMapper: ObjectMapper) {

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd"
        private const val DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
        private val ZONED_DATETIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT)
        private val LOCAL_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
    }

    @PostConstruct
    fun postConstruct() {
        val module = SimpleModule()
        module.addSerializer(ZonedDateTime::class.java, ZonedDateTimeSerializer())
        module.addDeserializer(ZonedDateTime::class.java, ZonedDateTimeDeserializer())
        module.addSerializer(LocalDate::class.java, LocalDateSerializer())
        module.addDeserializer(LocalDate::class.java, LocalDateDeserializer())
        objectMapper.registerModule(module)
    }

    inner class ZonedDateTimeSerializer : StdSerializer<ZonedDateTime>(ZonedDateTime::class.java) {
        override fun serialize(p0: ZonedDateTime?, p1: JsonGenerator?, p2: SerializerProvider?) {
            p1?.writeString(
                try {
                    p0?.format(ZONED_DATETIME_FORMATTER)
                } catch (e: DateTimeParseException) {
                    null
                }
            )

        }
    }

    inner class ZonedDateTimeDeserializer : StdDeserializer<ZonedDateTime>(ZonedDateTime::class.java) {
        override fun deserialize(p0: JsonParser?, p1: DeserializationContext?): ZonedDateTime? {
            return try {
                ZonedDateTime.parse(p0?.valueAsString, LOCAL_DATE_FORMATTER)
            } catch (e: DateTimeParseException) {
                null
            }
        }
    }

    inner class LocalDateSerializer : StdSerializer<LocalDate>(LocalDate::class.java) {
        override fun serialize(p0: LocalDate?, p1: JsonGenerator?, p2: SerializerProvider?) {
            p1?.writeString(
                try {
                    p0?.format(LOCAL_DATE_FORMATTER)
                } catch (e: DateTimeParseException) {
                    null
                }
            )

        }
    }

    inner class LocalDateDeserializer : StdDeserializer<LocalDate>(LocalDate::class.java) {
        override fun deserialize(p0: JsonParser?, p1: DeserializationContext?): LocalDate? {
            return try {
                LocalDate.parse(p0?.valueAsString, LOCAL_DATE_FORMATTER)
            } catch (e: DateTimeParseException) {
                null
            }
        }
    }

}