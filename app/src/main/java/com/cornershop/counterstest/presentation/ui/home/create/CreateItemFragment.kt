package com.cornershop.counterstest.presentation.ui.home.create

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.FragmentCreateItemBinding
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.presentation.ui.DialogFactory
import com.cornershop.counterstest.presentation.ui.base.*
import com.cornershop.counterstest.presentation.ui.common.addCustomTextChangedListener
import com.cornershop.counterstest.presentation.ui.common.hideKeyboard
import com.cornershop.counterstest.presentation.ui.common.isEmpty
import com.cornershop.counterstest.presentation.ui.common.subscribe
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
        init()
        return binding.root
    }

    private fun init(){
        binding.tvExamples.text = getExamplesLabel()
        binding.tvExamples.movementMethod = LinkMovementMethod.getInstance()
        binding.tvExamples.highlightColor = Color.TRANSPARENT

        inputBlockValidator = {
            binding.inputTitle.isEmpty(
                onTrue = {
                    binding.saveButton.isEnabled = false
                    binding.saveButton.setTextColor(ContextCompat.getColor(requireContext(),R.color.gray))
                },
                onFalse = {
                    binding.saveButton.setTextColor(ContextCompat.getColor(requireContext(),R.color.orange))
                    binding.saveButton.isEnabled = true
                }
            )
        }

        // input textWatcher
        binding.inputTitle.addCustomTextChangedListener(
            onTextChanged = { inputBlockValidator.invoke() }
        )
        setListeners()
        subscribeToData()
    }

    private fun setListeners(){
        binding.ivClose.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.saveButton.setOnClickListener {
            mCreateItemViewModel.createCounter(Counter(id="",title= binding.inputTitle.text.toString(), count = 0))
            this.hideKeyboard()
        }
    }


    private fun getExamplesLabel() : SpannableStringBuilder {
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                findNavController().navigate(R.id.action_createItemFragment_to_createItemExampleFragment)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }

        val label = SpannableStringBuilder()
        label.append(this.getString(R.string.create_counter_disclaimer))
        label.setSpan(clickableSpan, START_INDEX_CLICK, label.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return label
    }

    private fun subscribeToData() {
        mCreateItemViewModel.countersViewState.subscribe(this, { viewState ->
            binding.saveButton.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            when (viewState) {
                is Loading -> {
                    binding.saveButton.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Success -> findNavController().navigateUp()
                is Error -> {
                    Toast.makeText(
                    requireContext(),
                    getString(R.string.error_creating_counter_title),
                    Toast.LENGTH_SHORT
                ).show()
                }
                is DialogError ->  DialogFactory.showNoInternetView(requireContext(), {}, viewState.error)
            }
        })
    }

    companion object{
        const val START_INDEX_CLICK = 36
    }


}