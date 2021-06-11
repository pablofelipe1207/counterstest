package com.cornershop.counterstest.presentation.ui.common

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.cornershop.counterstest.data.common.corutine.CoroutineContextProvider
import com.cornershop.counterstest.domain.model.Counter
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

inline fun ViewModel.launch(
    coroutineContext: CoroutineContext = CoroutineContextProvider().main,
    crossinline block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(coroutineContext) { block() }
}

inline fun <T> LiveData<T>.subscribe(owner: LifecycleOwner, crossinline onDataReceived: (T) -> Unit) =
    observe(owner, Observer { onDataReceived(it) })

fun List<Counter>.toShareString(): String {
    val builder = StringBuilder()
    this.forEach {
        builder.append(it.count.toString() + " x " + it.title + System.getProperty("line.separator"))
    }
    return builder.toString()
}

fun EditText.addCustomTextChangedListener(
    afterTextChanged: (editable: Editable?) -> Unit = {},
    beforeTextChanged: (charSequence: CharSequence?) -> Unit = {},
    onTextChanged: (charSequence: CharSequence?) -> Unit = {}
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged(s)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s)
        }
    })
}

fun TextInputEditText.isEmpty(onTrue: () -> Unit = {}, onFalse: (input: String) -> Unit) {
    this.text?.let {
        if (it.isEmpty()) {
            onTrue()
        } else {
            onFalse(it.toString())
        }
    }
}

fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun AppCompatActivity.showVirtualKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}

fun Fragment.showVirtualKeyboard() {
    val activity = this.activity
    if (activity is AppCompatActivity) {
        activity.showVirtualKeyboard()
    }
}

fun Fragment.hideKeyboard() {
    val activity = this.activity
    if (activity is AppCompatActivity) {
        activity.hideKeyboard()
    }
}