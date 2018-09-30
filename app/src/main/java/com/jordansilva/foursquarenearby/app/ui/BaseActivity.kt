package com.jordansilva.foursquarenearby.app.ui

import android.annotation.SuppressLint
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.jordansilva.foursquarenearby.infrastructure.util.extensions.notNull
import org.jetbrains.anko.findOptional
import com.jordansilva.foursquarenearby.app.R

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    val fragmentManager: FragmentManager by lazy { supportFragmentManager }

    protected fun configureToolbar(showHomeButton: Boolean = true) {
        val toolbar = findOptional<Toolbar>(R.id.toolbar)
        toolbar.notNull { setSupportActionBar(it) }
        supportActionBar.notNull {
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(showHomeButton)
            it.setDisplayShowTitleEnabled(true)
        }
    }

    fun setTitle(text: String) {
        title = text
        supportActionBar?.title = text
    }

    override fun setTitle(@StringRes resId: Int) {
        setTitle(getString(resId))
    }

    protected fun addFragment(@IdRes containerViewId: Int, vararg fragment: Fragment) {
        fragmentManager.let { fm ->
            val ft = fm.beginTransaction()
            fragment.forEach { ft.add(containerViewId, it) }
            ft.commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}