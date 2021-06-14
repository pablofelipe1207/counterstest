package com.cornershop.counterstest.presentation.ui.home.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.FragmentCreateItemExamplesBinding
import com.cornershop.counterstest.domain.model.Example
import com.cornershop.counterstest.presentation.ui.base.*
import com.cornershop.counterstest.presentation.ui.common.subscribe
import com.cornershop.counterstest.presentation.ui.home.create.adapter.ExampleAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class CreateItemExampleFragment : BaseFragment() {
    private var _binding: FragmentCreateItemExamplesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ExampleAdapter
    private val mCreateItemViewModel: CreateItemViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateItemExamplesBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init(){
        showExamples(getExamples())
        setListeners()
        subscribeToData()
    }

    private fun setListeners(){
        binding.ivClose.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun getExamples() : ArrayList<Example>{
        val examples = arrayListOf<Example>()
        val examplesList = resources.getStringArray(R.array.examplesList)
        examplesList.forEach { item ->
            val id = resources.getIdentifier(item, ARRAY_TYPE, requireContext().packageName)
            if(id!=EMPTY) examples.add(Example(item, ArrayList(Arrays.asList(*resources.getStringArray(id)))))
        }
        return examples
    }

    private fun showExamples(data : ArrayList<Example>){
        adapter = ExampleAdapter(requireContext(),data, mCreateItemViewModel)
        binding.rvExamples.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false )
        binding.rvExamples.adapter = adapter
    }

    private fun subscribeToData() {
        mCreateItemViewModel.countersViewState.subscribe(this, { viewState ->
            when (viewState) {
                is Loading -> {}
                is Success -> findNavController().navigate(R.id.action_createItemExampleFragment_to_mainFragment)
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

    companion object{
        const val ARRAY_TYPE = "array"
        const val EMPTY = 0
    }
}