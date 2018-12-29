package com.example.there.aroundmenow.util.ext

import androidx.recyclerview.widget.RecyclerView
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

fun <VH : RecyclerView.ViewHolder> DiscreteScrollView.initDefault(adapter: RecyclerView.Adapter<VH>) {
    this.adapter = InfiniteScrollAdapter.wrap(adapter)
    setHasFixedSize(true)
    setSlideOnFling(true)
    setItemTransitionTimeMillis(150)
    setItemTransformer(ScaleTransformer.Builder().setMinScale(0.8f).build())
}