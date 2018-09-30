package com.jordansilva.foursquarenearby.app.util

import android.view.View

interface OnItemClickViewListener<in T> {
    fun onClickItem(view: View, item : T)
}