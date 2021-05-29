package com.example.gokeep.view.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.gokeep.R
import com.example.gokeep.view.ui.fragment.CreateItemFragment
import com.example.gokeep.view.ui.fragment.HomeFragment


class MainActivity : AppCompatActivity() {
    
    private var TAG = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {

            /**
             Add HomeFragment to MainActivity
             */
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HomeFragment>(R.id.fragment_container_view)
            }
        }
    }

//    override fun onBackPressed() {
//        if (supportFragmentManager.backStackEntryCount > 0){
//            supportFragmentManager.popBackStack()
//        }
//        else {
//            super.onBackPressed();
//        }
//    }

    fun showFragment(layoutId: Int, param: String) {
        var fragment: Fragment? = null

        when(layoutId) {
            R.layout.fragment_create_item -> {
                fragment = CreateItemFragment.newInstance(param)
            }
        }

        if(fragment != null) {
            supportFragmentManager.popBackStackImmediate(fragment::class.java.name, 0).apply {
                if(!this) {
                    supportFragmentManager.commit {
                        setCustomAnimations(
                            R.anim.fragment_slide_in,
                            R.anim.fragment_fade_out,
                            R.anim.fragment_fade_in,
                            R.anim.fragment_slide_out
                        )
                        add(R.id.fragment_container_view, fragment)
                        addToBackStack(null)
                    }
                }
            }
        }

    }


}