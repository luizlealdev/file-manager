package dev.luizleal.filemanagerx.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dev.luizleal.filemanagerx.R
import dev.luizleal.filemanagerx.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        setupBottomNav()

        val fileList: MutableList<String> = ArrayList()
        val pathArr = mutableListOf("storage", "emulated", "0")
        val currentPath = pathArr.joinToString("/")

        File(currentPath).listFiles()?.forEach { file ->
            val fileName =  file.name
            fileList.add(fileName)
        }

//        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,fileList)
//        findViewById<ListView>(R.id.filelist).adapter = arrayAdapter
//        Log.d("List of files", fileList.joinToString("\n"))
    }

    private fun setupBottomNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        _binding.bottomNav.setupWithNavController(navController)
    }
}