package dev.luizleal.filemanagerx.utils

import java.util.Locale

class ConversionUtils {
    companion object {
        fun formatBytes(bytes: Long): String {
            val kb = 1024
            val mb = 1024 * kb
            val gb = 1024 * mb

            return when {
                bytes >= gb -> String.format(Locale("EN"), "%.2f GB", bytes.toDouble() / gb)
                bytes >= mb -> String.format(Locale("EN"), "%.2f MB", bytes.toDouble() / mb)
                bytes >= kb -> String.format(Locale("EN"), "%.2f KB", bytes.toDouble() / kb)
                else -> String.format(Locale("EN"), "%d B", bytes)
            }
        }
    }
}