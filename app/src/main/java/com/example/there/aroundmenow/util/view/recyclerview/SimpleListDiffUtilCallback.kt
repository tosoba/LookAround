package com.example.there.aroundmenow.util.view.recyclerview

import androidx.recyclerview.widget.DiffUtil

abstract class SimpleListDiffUtilCallback<T>(
    private val oldItems: List<T>,
    private val newItems: List<T>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    protected fun getItemPair(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Pair<T, T> = Pair(oldItems[oldItemPosition], newItems[newItemPosition])
}