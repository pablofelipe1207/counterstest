package com.cornershop.counterstest.presentation.ui.home.create.adapter

import android.content.Context
import android.view.View
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.LayoutExamplesItemBinding
import com.cornershop.counterstest.domain.model.Example
import com.cornershop.counterstest.presentation.ui.common.GenericAdapter
import java.util.*
import kotlin.collections.ArrayList

class ExampleAdapter (private val context: Context, exampleList: ArrayList<Example>) : GenericAdapter<Example, LayoutExamplesItemBinding>(exampleList) {
    override fun getLayoutResId(): Int {
        return R.layout.layout_examples_item
    }

    override fun onBindData(model: Example, position: Int, dataBinding: LayoutExamplesItemBinding) {
        dataBinding.tvItemName.text = model.title.capitalize(Locale.ROOT)
        setItems(model,dataBinding)
    }

    private fun setItems(model: Example, dataBinding: LayoutExamplesItemBinding) {
        dataBinding.lvExample.removeAllViews()
        model.items.forEach{ item ->  dataBinding.lvExample.addView(ExampleItem(context).setItem(item))  }
        dataBinding.lvExample.visibility = View.VISIBLE
    }

}