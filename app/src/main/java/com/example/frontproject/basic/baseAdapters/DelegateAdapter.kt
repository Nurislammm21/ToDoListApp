package com.example.frontproject.basic.baseAdapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.frontproject.basic.base.BaseViewHolder

abstract class DelegateAdapter<DelegateAdapterItem : Any, VB : ViewBinding, VH : BaseViewHolder<DelegateAdapterItem, VB>>(val modelClass: Class<out DelegateAdapterItem>) {

    abstract fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder<DelegateAdapterItem, VB>
    fun onBindViewHolder(holder: VH, model: DelegateAdapterItem) {
        holder.doBindings(model)
        holder.bind()
    }

    open fun onViewRecycled(viewHolder: VH) = Unit
    open fun onViewDetachedFromWindow(viewHolder: VH) = Unit
    open fun onViewAttachedToWindow(viewHolder: VH) = Unit
}