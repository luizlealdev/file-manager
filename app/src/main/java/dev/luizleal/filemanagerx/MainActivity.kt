package dev.luizleal.filemanagerx

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val fileList: MutableList<String> = ArrayList()
        val pathArr = mutableListOf("storage", "emulated", "0")
        val currentPath = pathArr.joinToString("/")

        File(currentPath).listFiles()?.forEach { file ->
            val fileName =  file.name
            fileList.add(fileName)
        }

        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,fileList)
        findViewById<ListView>(R.id.filelist).adapter = arrayAdapter
        Log.d("List of files", fileList.joinToString("\n"))
    }
}