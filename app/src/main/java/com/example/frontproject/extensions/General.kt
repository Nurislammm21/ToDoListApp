package com.example.frontproject.extensions

import android.app.Activity
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

//View
inline fun View.setAttrs(
    attrs: AttributeSet?,
    styleable: IntArray,
    crossinline body: (TypedArray) -> Unit
) {
    context.theme.obtainStyledAttributes(attrs, styleable, 0, 0)
        .apply {
            try {
                body.invoke(this)
            } finally {
                recycle()
            }
        }
}

//Activity
fun Activity.toast(message: String) {
    android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
}

//Fragment
fun Fragment.toast(message: String) {
    android.widget.Toast.makeText(requireContext(), message, android.widget.Toast.LENGTH_SHORT)
        .show()
}



fun Fragment.navigate(direction: NavDirections) {
    try {
        findNavController().navigate(direction)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Fragment.navigate(direction: NavDirections, extras: FragmentNavigator.Extras) {
    try {
        findNavController().navigate(direction, extras)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
