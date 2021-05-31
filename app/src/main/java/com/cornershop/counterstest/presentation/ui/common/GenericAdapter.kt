package com.cornershop.counterstest.presentation.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class GenericAdapter<T, D>(arrayList: ArrayList<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listItems: ArrayList<T> = arrayList

    abstract fun getLayoutResId(): Int

    abstract fun onBindData(model: T, position: Int, dataBinding: D)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val dataBinding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), getLayoutResId(), parent, false)
        return ItemViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(listItems.get(position), position, (holder as GenericAdapter<*, *>.ItemViewHolder).mDataBinding as D)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    open fun addItems(arrayList: ArrayList<T>) {
        listItems.clear()
        listItems.addAll(arrayList)
        notifyDataSetChanged()
    }

    open fun getItem(position: Int): T {
        return listItems[position]
    }

    inner class ItemViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var mDataBinding: D = binding as D
    }

}