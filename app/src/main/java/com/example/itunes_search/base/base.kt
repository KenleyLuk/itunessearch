package com.example.itunes_search.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.itunes_search.R
import io.reactivex.annotations.NonNull

abstract class BaseActivity : AppCompatActivity() {
    fun fragmentTransaction(@NonNull fragment: Fragment, bundle: Bundle? = null, addToBackStack: Boolean = false, backStackName: String = fragment::class.java.name) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (bundle != null)
            fragment.arguments = bundle
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(backStackName)
        }
        fragmentTransaction.replace(R.id.frameLayout, fragment, fragment::class.java.name)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE)
        fragmentTransaction.commitAllowingStateLoss()
    }

}