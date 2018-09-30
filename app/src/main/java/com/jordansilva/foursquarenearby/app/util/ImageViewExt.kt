package com.jordansilva.foursquarenearby.app.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.px
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation


/**
 * Created by jordansilva on 18/03/18.
 */
fun ImageView.loadUrl(imageUrl: String) {
    Picasso.get().load(imageUrl).into(this)
}

fun ImageView.loadUrlWithTransformation(imageUrl: String, transformation: Transformation) {
    Picasso.get().load(imageUrl).fit().centerCrop().transform(transformation).into(this)
}

fun ImageView.loadUrlCenterCrop(imageUrl: String) {
    Picasso.get().load(imageUrl).fit().centerCrop().into(this)
}

fun ImageView.loadUrl(imageUrl: String,
                      successCallback: () -> Unit,
                      errorCallback: (e: Exception?) -> Unit) {
    Picasso.get().load(imageUrl).into(this, object : Callback {
        override fun onError(e: Exception?) {
            errorCallback(e)
        }

        override fun onSuccess() {
            successCallback()
        }
    })
}

fun ImageView.loadUrlRounded(imageUrl: String, radius: Int = 10.px, margin: Int = 0) {
    val transformation = RoundedCornersTransformation(radius, margin)

    Picasso.get().load(imageUrl)
            .fit()
            .centerCrop()
            .transform(transformation).into(this)

}


fun getDrawable(context: Context, drawableId: Int): Drawable? {

    return when {
        Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP -> VectorDrawableCompat.create(context.resources, drawableId, null)
        else -> AppCompatResources.getDrawable(context, drawableId)
    }
}