package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private var _fragment: FragmentQuiz? = null
    private val fragment get() = _fragment

    private var _sharefragment: FragmentQuiz? = null
    private val sharefragment get() = _sharefragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(1)
    }

    private fun openFragment(pageNumber: Int) {
        _fragment = FragmentQuiz.newInstance(pageNumber)

        if (_fragment != null) attachFragment(_fragment!!)

        fragment?.setInterface(object : NavigationInterface {
            override fun setPageNumber(page: Int) {
                if (page != 6) openFragment(page)
                else openShareFragment()
            }
        })
    }

    private fun attachFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()
        }
    }

    private fun openShareFragment() {
        _sharefragment = FragmentShare.newInstance()
        if (_sharefragment != null) attachFragment(_sharefragment!!)
    }
}