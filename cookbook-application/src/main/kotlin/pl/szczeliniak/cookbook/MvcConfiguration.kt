package pl.szczeliniak.cookbook

import org.springframework.context.annotation.Configuration
import org.springframework.format.Formatter
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pl.szczeliniak.cookbook.JacksonConfiguration.Companion.LOCAL_DATE_FORMATTER
import pl.szczeliniak.cookbook.JacksonConfiguration.Companion.ZONED_DATETIME_FORMATTER
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*


@Configuration
class MvcConfiguration : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addFormatter(LocalDateFormatter())
        registry.addFormatter(ZonedDateTimeFormatter())
        super.addFormatters(registry)
    }

    class LocalDateFormatter : Formatter<LocalDate> {
        override fun print(`object`: LocalDate, locale: Locale): String {
            return `object`.format(LOCAL_DATE_FORMATTER)
        }

        override fun parse(text: String, locale: Locale): LocalDate {
            return LocalDate.parse(text, LOCAL_DATE_FORMATTER)
        }

    }

    class ZonedDateTimeFormatter : Formatter<ZonedDateTime> {
        override fun print(`object`: ZonedDateTime, locale: Locale): String {
            return `object`.format(ZONED_DATETIME_FORMATTER)
        }

        override fun parse(text: String, locale: Locale): ZonedDateTime {
            return ZonedDateTime.parse(text, ZONED_DATETIME_FORMATTER)
        }

    }

}