package com.cornershop.counterstest.presentation.ui.home.main

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.FragmentMainBinding
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.presentation.ui.DialogFactory
import com.cornershop.counterstest.presentation.ui.base.*
import com.cornershop.counterstest.presentation.ui.common.subscribe
import com.cornershop.counterstest.presentation.ui.common.toShareString
import com.cornershop.counterstest.presentation.ui.home.main.adapter.CounterAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CounterAdapter
    private val mViewModel: MainViewModel by viewModel()
    private var counters: ArrayList<Counter>? = null
    private val counterInterface = object : CounterAdapter.CounterAdapterInterface {
        override fun increaseCounter(counter: Counter) {
            mViewModel.increaseCounter(counter)
        }

        override fun decrementCounter(counter: Counter) {
            mViewModel.decrementCounter(counter)
        }

        override fun selectedCounter(counter: Counter) {
            if(counter.isSelected){
                mViewModel.selectCounter(counter)
            }else{
                mViewModel.unSelectCounter(counter)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setListeners()
        subscribeToData()
        mViewModel.getCounters()
        return binding.root
    }

    private fun subscribeToData() {
        mViewModel.countersViewState.subscribe(this, { viewState ->
            binding.progressBar.visibility = View.GONE
            when (viewState) {
                is Loading -> binding.progressBar.visibility = View.VISIBLE
                is Success -> showCounters(ArrayList(viewState.data))
                is Error, is NoInternetState -> showInternetErrorView()
                is DialogError ->  DialogFactory.showNoInternetView(requireContext(), {}, viewState.error)
            }
        })
    }

    private fun showEmptyView() {
        showRemoveHeader(false, arrayListOf())
        setBreadcrumb(arrayListOf())
        binding.rvCounters.visibility = View.GONE
        binding.retry.visibility = View.GONE
        binding.swipeContainer.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.title.visibility = View.VISIBLE
        binding.subTitle.visibility = View.VISIBLE
        binding.title.text = activity?.getText(R.string.no_counters)
        binding.subTitle.text = activity?.getText(R.string.no_counters_phrase)
    }

    private fun showInternetErrorView() {
        binding.rvCounters.visibility = View.GONE
        binding.retry.visibility = View.VISIBLE
        binding.swipeContainer.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.title.visibility = View.VISIBLE
        binding.subTitle.visibility = View.VISIBLE
        binding.title.text = activity?.getText(R.string.error_load_counters_title)
        binding.subTitle.text = activity?.getText(R.string.connection_error_description)
    }

    private fun setListeners() {
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_createItemFragment)
        }
        binding.ivClose.setOnClickListener {
            val selected = counters!!.filter { it.isSelected }
            selected.forEach { c ->
                c.isSelected = false
                mViewModel.unSelectCounter(c)
            }
        }
        binding.remove.setOnClickListener {
            counters?.let {
                val selected = it.filter { it.isSelected }
                val title = if(selected.size == 1) "Delete ${selected.first().title}" else "Delete ${selected.size} items"
                DialogFactory.showDeleteView(requireContext(), {
                    mViewModel.deleteCounter(selected)
                }, title)
            }
        }
        binding.share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, counters!!.filter { it.isSelected }.toShareString())
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, requireContext().getString(R.string.share))
            startActivity(shareIntent)
        }
        binding.swipeContainer.setOnRefreshListener {
            mViewModel.getCounters()
        }
        binding.retry.setOnClickListener {
            mViewModel.getCounters()
        }
        binding.tvSearchText.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }


    private fun showCounters(data: ArrayList<Counter>){
        counters = data
        if(data.isEmpty()){
            showEmptyView()
        }else{
            val selected = data.filter { it.isSelected }
            if(selected.isNotEmpty()){
                showRemoveHeader(true, selected)
            }else{
                showRemoveHeader(false, data)
            }
            setBreadcrumb(data)
            binding.swipeContainer.isRefreshing = false
            binding.title.visibility = View.GONE
            binding.retry.visibility = View.GONE
            binding.subTitle.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.rvCounters.visibility = View.VISIBLE
            binding.swipeContainer.visibility = View.VISIBLE
            adapter = CounterAdapter(requireContext(),data,counterInterface)
            binding.rvCounters.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.rvCounters.adapter = adapter
        }
    }


    private fun showRemoveHeader(isVisible : Boolean, data: List<Counter>){
        if(isVisible){
            binding.title.visibility = View.GONE
            binding.retry.visibility = View.GONE
            binding.subTitle.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.ivClose.visibility = View.VISIBLE
            binding.tvSearchText.visibility = View.GONE
            binding.share.visibility = View.VISIBLE
            binding.remove.visibility = View.VISIBLE
            binding.headerTitle.visibility =View.VISIBLE
            binding.headerTitle.setText(requireContext().getString(R.string.n_selected,data.size))
        }else{
            binding.ivClose.visibility = View.GONE
            binding.tvSearchText.visibility = View.VISIBLE
            binding.share.visibility = View.GONE
            binding.remove.visibility = View.GONE
            binding.headerTitle.visibility = View.GONE
        }
    }

    private fun setBreadcrumb(data: List<Counter>){
        if(data.isNotEmpty()) {
            val allTimes = data.sumBy { it.count }
            val primaryForegroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(requireContext(),R.color.orange))
            val secondaryForegroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(requireContext(),R.color.gray))
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