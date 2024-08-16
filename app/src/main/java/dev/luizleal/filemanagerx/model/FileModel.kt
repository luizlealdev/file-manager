package dev.luizleal.filemanagerx.model

data class FileModel(
    val isDirectory: Boolean, //folder is true, file is false
    val name: String,
    val itemsQuantity: Int? = null,
    val size: Long? = null,
    val creationDate: String
)