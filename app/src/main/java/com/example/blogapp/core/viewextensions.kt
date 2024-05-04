package com.example.blogapp.core

import android.view.View

// extension functions
fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}