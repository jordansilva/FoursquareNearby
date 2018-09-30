package com.jordansilva.foursquarenearby.infrastructure.util.extensions

import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable

inline var TextView.drawableRight: Int
    get() = 0
    set(value) {
        setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(context, value), null)
    }

inline var TextView.drawableLeft: Int
    get() = 0
    set(value) {
        setCompoundDrawablesWithIntrinsicBounds(getDrawable(context, value), null, null, null)
    }
