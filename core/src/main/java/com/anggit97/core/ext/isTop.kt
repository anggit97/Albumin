package com.anggit97.core.ext

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.isTop(): Boolean {
    return canScrollVertically(-1).not()
}

fun RecyclerView.isBottom(): Boolean {
    return canScrollVertically(1).not()
}

fun RecyclerView.isScrolling(): Boolean {
    return scrollState != RecyclerView.SCROLL_STATE_IDLE
}
