package com.example.itunes_search.module.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.itunes_search.R
import com.example.itunes_search.databinding.ItemSearchBinding
import com.example.itunes_search.model.ResultModel

class SearchListAdapter(private val clickListener:(ResultModel)->Unit): RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {
    private var itemList = listOf<ResultModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSearchBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_search,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(itemList[position], clickListener)
    }

    fun updateData(itemList: List<ResultModel>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(resultModel: ResultModel, clickListener: (ResultModel) -> Unit) {
            binding.resultModel = resultModel
            binding.rlRoot.setOnClickListener { clickListener(resultModel) }
        }
    }
}