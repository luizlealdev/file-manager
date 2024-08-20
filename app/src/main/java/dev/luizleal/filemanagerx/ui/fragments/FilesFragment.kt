package dev.luizleal.filemanagerx.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.luizleal.filemanagerx.R
import dev.luizleal.filemanagerx.databinding.FragmentFilesBinding
import dev.luizleal.filemanagerx.model.FileModel
import dev.luizleal.filemanagerx.ui.adapter.FileListAdapter
import dev.luizleal.filemanagerx.ui.adapter.FolderPathListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class FilesFragment : Fragment(R.layout.fragment_files) {
    //binding variables
    private var _binding: FragmentFilesBinding? = null
    private val binding get() = _binding!!

    //file list adapter for set our recycler view
    private lateinit var fileListAdapter: FileListAdapter
    private lateinit var foldersPathList: FolderPathListAdapter

    /**
     * this variable contains the path that will be listed, for example, if the
     * user enters in the 'Downloads' directory, put "Downloads" into the list
     */
    private var currentPath = mutableListOf("storage", "emulated", "0")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFilesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //function to setup file list
        setupFoldersPathListRecyclerView()
        setupFileListRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()

        //set binding to null
        _binding = null
    }

    private fun setupFileListRecyclerView() {
        //define the recyclerview adapter
        fileListAdapter = FileListAdapter(requireContext()) { folderName ->
            currentPath.add(folderName)
            openFolder(currentPath)
        }

        binding.recyclerviewFiles.apply {
            adapter = fileListAdapter //set adapter in the recyclerview
            layoutManager = LinearLayoutManager( //set the layoutManager type
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        openFolder(currentPath)
    }

    private fun setupFoldersPathListRecyclerView() {
        foldersPathList = FolderPathListAdapter { folderPosition ->
            currentPath.subList(folderPosition + 1, currentPath.size).clear()

            openFolder(currentPath)
        }

        binding.foldersPathList.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = foldersPathList
        }
    }

    private fun openFolder(path: List<String>) {
        binding.progressIndicatorLoading.visibility = View.VISIBLE

        //set file by the variable currentPath
        getFileFromDirectory(path) { files ->
            if (files.isNotEmpty()) {
                fileListAdapter.setFiles(files)
                binding.progressIndicatorLoading.visibility = View.GONE

                binding.foldersPathList.smoothScrollToPosition(path.size - 1)
            }
        }

        foldersPathList.setFolderList(path)
    }

    private fun getFileFromDirectory(pathList: List<String>, callback: (List<FileModel>) -> Unit) {
        val path = pathList.joinToString("/") //add string between each item of the list
        val fileList: MutableList<FileModel> =
            ArrayList() //create a variable to store the file list
        val dateFormater = SimpleDateFormat("dd/MM/yyyy", Locale("US"))

        lifecycleScope.launch {
            withContext(Dispatchers.IO) { //move the code to thread I/O for avoid the application freeze
                File(path).listFiles()
                    ?.map { file -> //from the currentPath, get all files and folders and list it
                        //check if the current item is a directory, if it is, get the total items inside the directory
                        val itemsQuantity =
                            if (file.isDirectory) file.listFiles()?.size ?: 0 else null

                        //check if the current item is a file, if it is, get the file size in bytes
                        val size = if (file.isFile) file.length() else null
                        val lastModifiedDate = file.lastModified()

                        //add the current file to the list
                        fileList.add(
                            FileModel(
                                isDirectory = file.isDirectory,
                                name = file.name,
                                itemsQuantity = itemsQuantity,
                                size = size,
                                creationDate = dateFormater.format(
                                    lastModifiedDate
                                ) //format the date to 00/00/0000
                            )
                        )
                    } ?: listOf()
            }
            //sort the list
            callback(fileList.sortedWith(compareByDescending<FileModel> { it.isDirectory }.thenBy { it.name }))
        }
    }
}