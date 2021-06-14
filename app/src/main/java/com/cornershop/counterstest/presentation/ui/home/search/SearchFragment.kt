package com.cornershop.counterstest.presentation.ui.home.search

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.FragmentSearchBinding
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.presentation.ui.base.*
import com.cornershop.counterstest.presentation.ui.common.addCustomTextChangedListener
import com.cornershop.counterstest.presentation.ui.common.hideKeyboard
import com.cornershop.counterstest.presentation.ui.common.showVirtualKeyboard
import com.cornershop.counterstest.presentation.ui.common.subscribe
import com.cornershop.counterstest.presentation.ui.home.search.adapter.SearchAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val sViewModel: SearchViewModel by viewModel()
    private lateinit var adapter: SearchAdapter
    var search: String? = null
    var isFirstSearch = true
    private val counterInterface = object : SearchAdapter.SearchAdapterInterface {
        override fun increaseCounter(counter: Counter) {
            sViewModel.increaseCounter(counter)
        }
        override fun decrementCounter(counter: Counter) {
            sViewModel.decrementCounter(counter)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        sViewModel.getCounters()
        binding.tvSearchText.requestFocus()
        showVirtualKeyboard()
        setListeners()
        subscribeToData()
    }

    private fun setListeners(){
        binding.ivBack.setOnClickListener {
            hideKeyboard()
            findNavController().navigateUp()
        }
        binding.tvSearchText.addCustomTextChangedListener(onTextChanged = {
            search = it.toString()
            sViewModel.searchCounterByTitle(it.toString())
        })
    }

    private fun subscribeToData() {
        sViewModel.countersViewState.subscribe(this, { viewState ->
            when (viewState) {
                is Loading -> {}
                is Success -> showCounters(ArrayList(viewState.data))
                is Error -> Toast.makeText(
                    requireContext(),
                    getString(R.string.error_creating_counter_title),
                    Toast.LENGTH_SHORT
                ).show()
                is NoInternetState -> Toast.makeText(
                    requireContext(), getString(R.string.error_creating_counter_title),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showCounters(data: ArrayList<Counter>) {
        if(isFirstSearch.not())binding.body.setBackgroundColor(Color.TRANSPARENT)
        isFirstSearch = false
        setBreadcrumb(data)
        if(data.isEmpty()){
            binding.title.visibility = View.VISIBLE
            binding.rvCounters.visibility = View.GONE
        }else{
            binding.title.visibility = View.GONE
            binding.rvCounters.visibility = View.VISIBLE
            adapter = SearchAdapter(requireContext(), data, counterInterface)
            binding.rvCounters.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.rvCounters.adapter = adapter
        }
    }

    private fun setBreadcrumb(data: List<Counter>){
        if(data.isNotEmpty()) {
            val allTimes = data.sumBy { it.count }
            val primaryForegroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.black))
            val secondaryForegroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.gray))
            val label = SpannableStringBuilder()
            label.bold { append(requireContext().getString(R.string.n_items,data.size)+" ") }
            label.setSpan(primaryForegroundColorSpan, 0, label.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            val secondaryLabel = label.length
            label.append(requireContext().getString(R.string.n_times,allTimes))
            label.setSpan(secondaryForegroundColorSpan, secondaryLabel, label.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            binding.breadcrumb.visibility = View.VISIBLE
            binding.breadcrumb.text = label
        } else {
            binding.breadcrumb.visibility = View.GONE
        }
    }


}