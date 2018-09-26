package com.jordansilva.foursquarenearby.app.ui

import android.annotation.SuppressLint
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    val fragmentManager: FragmentManager by lazy { supportFragmentManager }

    fun setTitle(title: String) {
//        val toolbar = findOptional<Toolbar>(R.id.toolbar)
//        toolbar?.let {
//            val toolbarTitle = findOptional<TextView>(R.id.toolbarTitle)
//            toolbarTitle?.let {
//                it.text = title
//                toolbar.title = ""
//            } ?: run { toolbar.title = title }
//        }
    }

    override fun setTitle(@StringRes resId: Int) {
        setTitle(getString(resId))
    }

    fun setSubtitle(@StringRes resId: Int) {
        setSubtitle(getString(resId))
    }

    fun setSubtitle(subtitle: String) {
//        val toolbar = findOptional<Toolbar>(R.id.toolbar)
//        toolbar?.let {
//            val toolbarSubtitle = findOptional<TextView>(R.id.toolbarSubtitle)
//            toolbarSubtitle?.isVisible = !subtitle.isBlank()
//            toolbarSubtitle?.text = subtitle
//        }
    }

    protected fun addFragment(@IdRes containerViewId: Int, vararg fragment: Fragment) {
        fragmentManager.let { fm ->
            val ft = fm.beginTransaction()
            fragment.forEach { ft.add(containerViewId, it) }
            ft.commit()
        }
    }

}