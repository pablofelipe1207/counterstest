package com.cornershop.counterstest.presentation.ui.home.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cornershop.counterstest.databinding.FragmentCreateItemBinding
import com.cornershop.counterstest.presentation.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateItemFragment : BaseFragment() {
    private var _binding: FragmentCreateItemBinding? = null
    private val binding get() = _binding!!
    private val mCreateItemViewModel: CreateItemViewModel by viewModel()

    private lateinit var inputBlockValidator: () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateItemBinding.inflate(inflater, container, false)
        return binding.root
    }

}