package com.jeric.bitteldigitalsignage.network.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.Fragment

sealed class DataState<T>(var data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null): DataState<T>(data)
    class Success<T>(data: T?): DataState<T>(data)
    class Error<T>(message: String, data: T? = null): DataState<T>(data, message)
}


fun openApp(activity: Context) {
    val intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_LAUNCHER) // Set category to launcher
        flags = Intent.FLAG_ACTIVITY_NEW_TASK // Create a new task for the activity
        component = ComponentName(
            "com.jeric.tvapp",
            "com.jeric.tvapp.splash.CustomSplash"
        ) // Set the specific activity to launch
    }
    activity.startActivity(intent) // Start the activity
}

fun Fragment.attachFragment(fragment: Fragment, containerId: Int, tag: String) {
    val container = requireView().findViewById<ViewGroup>(containerId) // Find the container view
    container?.let {
        // Check if the fragment is not already added
        if (childFragmentManager.findFragmentByTag(tag) == null) {
            childFragmentManager.beginTransaction() // Begin a fragment transaction
                .replace(containerId, fragment, tag) // Replace the container with the new fragment
                .commitNowAllowingStateLoss() // Commit the transaction
        }
    } ?: run {
        // Log an error if the container is not found
        Log.e(javaClass.simpleName, "Container View not found")
    }
}