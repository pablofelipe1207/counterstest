package com.cornershop.counterstest.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

abstract class BaseFragment : Fragment(), LifecycleObserver {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        lifecycle.addObserver(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    override fun onResume() { super.onResume() }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    @Override
    override fun onPause() { super.onPause() }

}