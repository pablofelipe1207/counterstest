package com.cornershop.counterstest.presentation.ui.home.main.adapter

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.LayoutMainCounterItemBinding
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.presentation.ui.common.GenericAdapter

class CounterAdapter(private val context: Context, private val currentCounterList: ArrayList<Counter>, private val _interface: CounterAdapterInterface) : GenericAdapter<Counter, LayoutMainCounterItemBinding>(currentCounterList) {

    fun setItems(counterList: ArrayList<Counter>) {
        currentCounterList.clear()
        currentCounterList.addAll(counterList)
        this.notifyDataSetChanged()
    }

    override fun getLayoutResId(): Int {
        return R.layout.layout_main_counter_item
    }

    override fun onBindData(model: Counter, position: Int, dataBinding: LayoutMainCounterItemBinding) {
        dataBinding.tvItemName.text = model.title
        dataBinding.tvCounter.text = model.count.toString()
        enablesDecreaseButton(model, dataBinding)

        dataBinding.ivMinus.setOnClickListener {
            _interface.decrementCounter(model)
        }
        dataBinding.ivPlus.setOnClickListener {
            _interface.increaseCounter(model)
        }
        dataBinding.parent.setOnLongClickListener {
            model.isSelected = model.isSelected.not()
            isSelectedView(model.isSelected, dataBinding)
            _interface.selectedCounter(model)
            return@setOnLongClickListener true
        }
        isSelectedView(model.isSelected, dataBinding)
    }

    private fun enablesDecreaseButton(model: Counter, dataBinding: LayoutMainCounterItemBinding) {
        dataBinding.ivMinus.setColorFilter(ContextCompat.getColor(context, if (model.count == 0) R.color.light_gray else R.color.orange), android.graphics.PorterDuff.Mode.SRC_IN)
        dataBinding.ivMinus.isEnabled = !(model.count == 0)
    }

    interface CounterAdapterInterface {
        fun increaseCounter(counter: Counter)
        fun decrementCounter(counter: Counter)
        fun selectedCounter(counter: Counter)
    }

    private fun isSelectedView(isSelected: Boolean, dataBinding: LayoutMainCounterItemBinding) {
        if (isSelected) {
            dataBinding.ivCheck.visibility = View.VISIBLE
            dataBinding.parent.setBackgroundResource(R.drawable.ic_selected_item)
            dataBinding.ivPlus.visibility = View.GONE
            dataBinding.ivMinus.visibility = View.GONE
            dataBinding.tvCounter.visibility = View.GONE
        } else {
            dataBinding.ivCheck.visibility = View.GONE
            dataBinding.parent.setBackgroundResource(0)
            dataBinding.ivPlus.visibility = View.VISIBLE
            dataBinding.ivMinus.visibility = View.VISIBLE
            dataBinding.tvCounter.visibility = View.VISIBLE
        }
    }

}