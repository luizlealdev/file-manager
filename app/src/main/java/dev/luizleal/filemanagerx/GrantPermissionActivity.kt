package dev.luizleal.filemanagerx

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dev.luizleal.filemanagerx.databinding.ActivityGrantPermissionBinding

class GrantPermissionActivity : AppCompatActivity() {

    private var _binding: ActivityGrantPermissionBinding? = null
    private val binding get() = _binding!!

    //Register a ActivityResultLauncher to get the storage permission on android 11 or over
    private val manageStoragePermission =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    //Permission was granted
                    openMainActivity()
                }
            }
        }

    //Register a ActivityResultLauncher to get the storage permission on android 10 or lower
    private val requestWriteStoragePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                //Permission was granted
                openMainActivity()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()

        _binding = ActivityGrantPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //check if the storage permission is already granted
        checkIfPermissionIsAlreadyGranted()

        //ask the user to get storage permission
        binding.apply {
            buttonGetPermission.setOnClickListener {
                getPermission()
                Log.d("Get permission", "clicked")
            }
            buttonDenyPermission.setOnClickListener {
                //when user click on "Not now", finish the activity
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getPermission() {
        //check if the user's android version is greater than version R (android 11)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //check if external storage permission is not granted
            if (!Environment.isExternalStorageManager()) {

                //if it isn't, create a intent to ask the permission
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                    data = Uri.parse("package:" + this.`package`)
                }

                //launch the intent to start the settings activity
                manageStoragePermission.launch(intent)
            }
        } else { //if android version is not greater than R, ask another type of permission

            //check if external storage permission is not granted
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //launch
                requestWriteStoragePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun checkIfPermissionIsAlreadyGranted() {
        //if (for android 10 or lower)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openMainActivity()

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { //for android 11 or above
            if (Environment.isExternalStorageManager()) {

                openMainActivity()
            }
        }
    }

    private fun openMainActivity() {
        //Create a MainActivity Intent
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        //start this activity
        startActivity(mainActivityIntent)
    }
}