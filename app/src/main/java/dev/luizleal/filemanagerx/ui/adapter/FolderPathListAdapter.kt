package dev.luizleal.filemanagerx.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.luizleal.filemanagerx.databinding.DirectoryFolderItemBinding

class FolderPathListAdapter(private val onClicked: (position: Int) -> Unit) :
    RecyclerView.Adapter<FolderPathListAdapter.FolderListViewHolder>() {
    private var folderList: MutableList<String> = ArrayList()

    fun setFolderList(list: List<String>) {
        this.folderList = list.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DirectoryFolderItemBinding.inflate(inflater, parent, false)

        return FolderListViewHolder(binding)
    }

    override fun getItemCount(): Int = folderList.size

    override fun onBindViewHolder(holder: FolderListViewHolder, position: Int) {
        holder.bind(folderList[position], position, onClicked)
    }

    class FolderListViewHolder(private val binding: DirectoryFolderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(folder: String, position: Int, onClicked: (position: Int) -> Unit) {
            binding.textFolderName.text = folder

            binding.textFolderName.setOnClickListener {
                onClicked(position)
            }
        }
    }

}