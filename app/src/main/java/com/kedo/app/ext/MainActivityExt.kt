package com.kedo.app.ext

import com.kedo.app.MainActivity
import com.kedo.app.R
import com.kedo.app.fragment.SplashFragment

fun MainActivity.showSplashFragment() {
    SplashFragment.newInstance().let { splashFragment ->
        supportFragmentManager.beginTransaction()
            .add(R.id.mContainerView, splashFragment)
            .show(splashFragment)
            .commitAllowingStateLoss()
    }
}

fun MainActivity.hideSplashFragment() {

}