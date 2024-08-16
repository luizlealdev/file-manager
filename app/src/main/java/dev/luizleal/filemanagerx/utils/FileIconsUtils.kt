package dev.luizleal.filemanagerx.utils

import dev.luizleal.filemanagerx.R

class FileIconsUtils {
    companion object {
        private val imagePattern = Regex("\\.(png|jpg|jpeg|webp|gif|bmp|tiff)$")
        private val audioPattern = Regex("\\.(mp3|wav|aac|flac|ogg|m4a)$")
        private val videoPattern = Regex("\\.(mp4|avi|mkv|mov|wmv|flv|webm)$")
        private val documentPattern = Regex("\\.(doc|docx|odt)$")
        private val sheetPattern = Regex("\\.(xls|xlsx|ods|csv)$")
        private val presentationPattern = Regex("\\.(ppt|pptx|odp)$")
        private val compactedPattern = Regex("\\.(zip|rar|7z|tar|gz)$")
        private val exePattern = Regex("\\.(apk|exe|bat|sh)$")
        private val codePattern = Regex("\\.(java|py|js|html|css|cpp|c|h|kt|xml|json)$")
        private val dbPattern = Regex("\\.(db|sql|sqlite|mdb)$")
        private val fontPattern = Regex("\\.(ttf|otf|woff)$")

        fun getFileIcon(fileName: String): Int {
            return when {
                imagePattern.containsMatchIn(fileName) -> R.drawable.ic_file_image
                videoPattern.containsMatchIn(fileName) -> R.drawable.ic_file_video
                audioPattern.containsMatchIn(fileName) -> R.drawable.ic_file_audio
                documentPattern.containsMatchIn(fileName) -> R.drawable.ic_file_doc
                fileName.contains(".pdf") -> R.drawable.ic_file_pdf
                sheetPattern.containsMatchIn(fileName) -> R.drawable.ic_file_sheet
                presentationPattern.containsMatchIn(fileName) -> R.drawable.ic_file_presentation
                compactedPattern.containsMatchIn(fileName) -> R.drawable.ic_file_zip
                exePattern.containsMatchIn(fileName) -> R.drawable.ic_file_exe
                codePattern.containsMatchIn(fileName) -> R.drawable.ic_file_code
                dbPattern.containsMatchIn(fileName) -> R.drawable.ic_file_db
                fontPattern.containsMatchIn(fileName) -> R.drawable.ic_file_font
                else -> R.drawable.ic_file_default
            }
        }

        fun getFolderIcon(folderName: String): Int {
            return when (folderName.lowercase()) {
                "android" -> R.drawable.ic_folder_android
                "download" -> R.drawable.ic_folder_download
                "documents" -> R.drawable.ic_folder_document
                "pictures" -> R.drawable.ic_folder_image
                "music" -> R.drawable.ic_folder_music
                "movies" -> R.drawable.ic_folder_video
                "dcim" -> R.drawable.ic_folder_camera
                else -> R.drawable.ic_folder_default
            }
        }
    }
}