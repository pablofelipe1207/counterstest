package com.cornershop.counterstest.presentation.ui

import android.content.Context
import com.cornershop.counterstest.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder


object DialogFactory {

   fun showNoInternetView(context: Context, onPositive: () -> Unit, title : String? = null) {
       val newTitle = title?.let { it }?: kotlin.run { context.resources.getString(R.string.error_creating_counter_title) }
       val dialog = MaterialAlertDialogBuilder(context)
              .setTitle(newTitle)
              .setMessage(context.resources.getString(R.string.connection_error_description))
              .setPositiveButton(context.resources.getString(R.string.ok)) { dialog, which ->
                 dialog.dismiss()
                 onPositive.invoke()
              }
      dialog.show()
   }
    fun showDeleteView(context: Context, onPositive: () -> Unit, title : String? = null) {
        val dialog = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setPositiveButton(context.resources.getString(R.string.delete)) { dialog, which ->
                dialog.dismiss()
                onPositive.invoke()
            }
            .setNegativeButton(context.resources.getString(R.string.cancel)){dialog, which ->
                dialog.dismiss()
            }
        dialog.show()
    }
}