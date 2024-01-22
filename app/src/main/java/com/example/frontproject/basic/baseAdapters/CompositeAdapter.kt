package com.example.frontproject.basic.baseAdapters

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.example.frontproject.basic.base.BaseViewHolder

@Suppress("UNCHECKED_CAST")
class CompositeAdapter(
    private val delegates: SparseArray<DelegateAdapter<DelegateAdapterItem, ViewBinding, BaseViewHolder<DelegateAdapterItem, ViewBinding>>>
) : ListAdapter<DelegateAdapterItem, BaseViewHolder<DelegateAdapterItem, ViewBinding>>(
    DelegateAdapterItemDiffCallback()
) {

    override fun getItemViewType(position: Int): Int {
        for (i in 0 until delegates.size()) {
            if (delegates[i].modelClass == getItem(position).javaClass) {
                return delegates.keyAt(i)
            }
        }
        throw NullPointerException("Can not get viewType for position $position")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): BaseViewHolder<DelegateAdapterItem, ViewBinding> =
        delegates[viewType].onCreateViewHolder(parent)

    override fun onBindViewHolder(
        holder: BaseViewHolder<DelegateAdapterItem, ViewBinding>,
        position: Int,
    ) {
        val delegateAdapter = delegates[getItemViewType(position)]

        if (delegateAdapter != null) {
            delegateAdapter.onBindViewHolder(
                holder,
                getItem(position)
            ) // need to check model is ok or need position as int
        } else {
            throw NullPointerException("can not find adapter for position $position")
        }
    }

    override fun onViewRecycled(holder: BaseViewHolder<DelegateAdapterItem, ViewBinding>) {
        delegates[holder.itemViewType].onViewRecycled(holder)
        super.onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<DelegateAdapterItem, ViewBinding>) {
        delegates[holder.itemViewType].onViewDetachedFromWindow(holder)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<DelegateAdapterItem, ViewBinding>) {
        delegates[holder.itemViewType].onViewAttachedToWindow(holder)
        super.onViewAttachedToWindow(holder)
    }

    override fun submitList(items: List<DelegateAdapterItem>?) {
        super.submitList(items ?: emptyList())
    }

    class Builder {

        private var count: Int = 0
        private val delegates: SparseArray<DelegateAdapter<DelegateAdapterItem, ViewBinding, BaseViewHolder<DelegateAdapterItem, ViewBinding>>> =
            SparseArray()

        fun add(delegateAdapter: DelegateAdapter<out DelegateAdapterItem, out ViewBinding, out BaseViewHolder<out DelegateAdapterItem, out ViewBinding>>): Builder {
            delegates.put(
                count++,
                delegateAdapter as DelegateAdapter<DelegateAdapterItem, ViewBinding, BaseViewHolder<DelegateAdapterItem, ViewBinding>>
            )
            return this
        }

        fun build(): CompositeAdapter {
            require(count != 0) { "Register at least one adapter" }
            return CompositeAdapter(delegates)
        }
    }
}