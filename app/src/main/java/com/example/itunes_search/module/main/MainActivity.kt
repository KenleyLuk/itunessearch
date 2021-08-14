package com.example.itunes_search.module.main

import android.os.Bundle
import com.example.itunes_search.R
import com.example.itunes_search.base.BaseActivity
import com.example.itunes_search.module.search.fragment.SearchFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().let {
            it.replace(R.id.frameLayout, SearchFragment())
            it.commit()
            true
        }
    }
}