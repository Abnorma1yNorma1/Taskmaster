package com.example.taskmaster.ui.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmaster.databinding.ItemTagBinding
import com.example.taskmaster.model.Tag

class TagRecyclerAdapter: RecyclerView.Adapter<TagRecyclerAdapter.TagViewHolder>() {

    private var tagList: MutableList<Tag> = mutableListOf()

    inner class TagViewHolder(private val tagBinding: ItemTagBinding):
            RecyclerView.ViewHolder(tagBinding.root){
                fun bind(tag: Tag){
                    tagBinding.tagText.text = tag.tagName
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val tagBinding =
            ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagViewHolder(tagBinding)
    }

    override fun getItemCount(): Int {
       return tagList.size
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
       val tag = tagList[position]
        holder.bind(tag)
    }

    fun setData(newList: List<Tag>) {
        val diffTag = TagDiffCallback(tagList, newList)
        val result = DiffUtil.calculateDiff(diffTag)
        tagList.clear()
        tagList.addAll(newList)
        result.dispatchUpdatesTo(this)
    }
}

class TagDiffCallback(private val oldList: List<Tag>, private val newList: List<Tag>): DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id==newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

}