package pl.szczeliniak.kitchenassistant.photo

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import pl.szczeliniak.kitchenassistant.photo.dto.response.DeletePhotoResponse
import pl.szczeliniak.kitchenassistant.photo.dto.response.UploadPhotoResponse
import javax.transaction.Transactional

@RestController
@RequestMapping("/photos")
@Validated
class PhotoController(private val photoService: PhotoService) {

    @Transactional
    @PostMapping
    fun uploadPhoto(@RequestParam("file") file: MultipartFile): UploadPhotoResponse {
        return photoService.upload(file.originalFilename ?: file.name, file.bytes)
    }

    @GetMapping("/{fileName}")
    fun downloadPhoto(@PathVariable fileName: String): ResponseEntity<ByteArray> {
        val response = photoService.download(fileName)
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(response.mediaType.mimeType))
            .body(response.body)
    }

    @Transactional
    @DeleteMapping("/{fileName}")
    fun deletePhoto(@PathVariable fileName: String): DeletePhotoResponse {
        return photoService.delete(fileName)
    }

}