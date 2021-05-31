package com.cornershop.counterstest.presentation.ui.home.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cornershop.counterstest.databinding.FragmentCreateItemExamplesBinding
import com.cornershop.counterstest.presentation.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateItemExampleFragment : BaseFragment() {
    private var _binding: FragmentCreateItemExamplesBinding? = null
    private val binding get() = _binding!!
    private val mCreateItemViewModel: CreateItemViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateItemExamplesBinding.inflate(inflater, container, false)
        return binding.root
    }
}