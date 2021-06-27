package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private var _fragment: FragmentQuiz? = null
    private val fragment get() = _fragment

    private var _sharefragment: FragmentShare? = null
    private val sharefragment get() = _sharefragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(1)
    }

    private fun openFragment(pageNumber: Int) {
        _fragment = FragmentQuiz.newInstance(pageNumber)

        if (fragment != null) attachFragment(fragment!!)

        fragment?.setInterface(object : NavigationInterface {
            override fun setPageNumber(page: Int) {
                if (page in 1..5) openFragment(page)
                else if (page == 6) openShareFragment()
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
        if (sharefragment != null) attachFragment(sharefragment!!)

        sharefragment?.setShareInterface(object : RestoreInterface {
            override fun goToFirstPage() {
                openFragment(1)
            }
        })
    }

    companion object {
        var userAnswerList = MutableList(5) { 0 }
    }
}