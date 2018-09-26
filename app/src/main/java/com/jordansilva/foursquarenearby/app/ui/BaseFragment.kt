package com.jordansilva.foursquarenearby.app.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

open class BaseFragment : Fragment() {

    val fragmentManagerChild: FragmentManager by lazy { activity!!.supportFragmentManager }

    protected fun addFragment(@IdRes containerViewId: Int, vararg fragment: Fragment) {
        fragmentManagerChild.let { fm ->
            val ft = fm.beginTransaction()
            fragment.forEach { ft.add(containerViewId, it) }
            ft.commit()
        }
    }
}