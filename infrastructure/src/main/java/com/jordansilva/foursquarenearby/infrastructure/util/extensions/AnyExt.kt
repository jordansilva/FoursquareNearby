package com.jordansilva.foursquarenearby.infrastructure.util.extensions

import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


/**
 * Created by jordansilva on 15/03/18.
 */
inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? {
    return input?.let(callback)
}

fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}

fun <T : Any> T?.isNull(f: (it: T?) -> Unit) {
    if (this == null) f(this)
}

fun <T : CharSequence> T?.notNullOrEmpty(f: (it: T) -> Unit) {
    if (this != null && this.isNotEmpty()) f(this)
}

fun <T : Any> List<T>?.isNotNullOrEmpty() = (this != null && this.isNotEmpty())

fun <T : Any> List<T>?.notNullOrEmpty(f: (it: List<T>) -> Unit) {
    if (this != null && this.isNotEmpty()) f(this)
}

val Int.dp: Float
    get() = (this / Resources.getSystem().displayMetrics.density)

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

inline fun <T, reified R> List<T>.mapToTypedArray(transform: (T) -> R): Array<R> {
    return when (this) {
        is RandomAccess -> Array(size) { index -> transform(this[index]) }
        else -> with(iterator()) { Array(size) { transform(next()) } }
    }
}