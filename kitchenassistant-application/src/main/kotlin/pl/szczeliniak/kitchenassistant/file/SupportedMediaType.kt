package pl.szczeliniak.kitchenassistant.file

enum class SupportedMediaType(val extension: String, val mimeType: String) {

    PNG(".png", "image/png"),
    JPG(".jpg", "image/jpeg"),
    JPEG(".jpeg", "image/jpeg");

    companion object {
        fun byExtension(fileName: String): SupportedMediaType? {
            return values().firstOrNull { mediaType -> fileName.endsWith(mediaType.extension) }
        }
    }
}