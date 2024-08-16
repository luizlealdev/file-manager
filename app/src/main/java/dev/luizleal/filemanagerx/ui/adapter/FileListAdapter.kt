package dev.luizleal.filemanagerx.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.luizleal.filemanagerx.R
import dev.luizleal.filemanagerx.databinding.FileItemHolderBinding
import dev.luizleal.filemanagerx.model.FileModel
import dev.luizleal.filemanagerx.utils.ConversionUtils.Companion.formatBytes
import dev.luizleal.filemanagerx.utils.FileIconsUtils.Companion.getFileIcon
import dev.luizleal.filemanagerx.utils.FileIconsUtils.Companion.getFolderIcon

class FileListAdapter(private val context: Context) :
    RecyclerView.Adapter<FileListAdapter.FileViewHolder>() {

    //file list the will be in the future the items of recycler view
    private var fileList: MutableList<FileModel> = ArrayList()

    //set function for fileList
    fun setFiles(files: List<FileModel>) {
        this.fileList = files.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        //create a inflater
        val inflater = LayoutInflater.from(parent.context)
        //create a binding from the recyclerview items and user the inflater above
        val binding = FileItemHolderBinding.inflate(inflater, parent, false)

        //return the viewHolder with the binding as a argument
        return FileViewHolder(context, binding)
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        when (holder) {
            is FileViewHolder -> {
                //call the function bind of the holder with the file list item in the current position
                holder.bind(fileList[position])
            }
        }
    }

    class FileViewHolder(private val context: Context, binding: FileItemHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //creating of variables from items in xml
        private val icon = binding.imageFileTypeIcon
        private val name = binding.textFileName
        private val info = binding.textFileInfo
        private val creationDate = binding.textFileDate

        fun bind(file: FileModel) {
            Log.d(file.name, file.isDirectory.toString())
            var infoText = ""

            if (file.isDirectory) {
                icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        getFolderIcon(file.name)
                    )
                )

                infoText =
                    "${file.itemsQuantity} ${ContextCompat.getString(context, R.string.items)}"
            } else {
                icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        getFileIcon(file.name)
                    )
                )

                infoText = file.size?.let { formatBytes(it) }.toString()
            }

            info.text = infoText
            name.text = file.name
            creationDate.text = file.creationDate
        }
    }
}