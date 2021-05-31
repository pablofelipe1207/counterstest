package com.cornershop.counterstest.presentation.ui.home.create.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.LayoutExampleItemBinding

class ExampleItem : FrameLayout {

    lateinit var dataBinding : LayoutExampleItemBinding

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttribute: Int) : super(context, attributeSet, defStyleAttribute) {
        init()
    }

    private fun init(){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.layout_example_item, this, true)
    }

    fun setItem(item: String): ExampleItem {
        dataBinding.tvItemName.text = item
        return this
    }

}