package net.okjsp

import org.springframework.web.multipart.MultipartFile
import java.io.File

class FileController {

    def image() {

        MultipartFile imageFile = request.getFile("files")

        if(!imageFile.empty) {
            def ext = imageFile.originalFilename.substring(imageFile.originalFilename.lastIndexOf('.'));
            def mil = System.currentTimeMillis()
            imageFile.transferTo(new File("${grailsApplication.config.grails.filePath}/images/", "${mil}${ext}"))

            render "<script>parent.\$.imageUploaded('${grailsApplication.config.grails.fileURL}/images/${mil}${ext}', '${mil}${ext}');</script>"
        }
    }
}
