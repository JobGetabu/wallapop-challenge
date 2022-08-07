package com.wallapop.util

import android.view.View

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}

fun <T> shuffle(list: MutableList<T>) {
    list.shuffle()
}