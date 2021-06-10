package jp.terameteo.dayaction202105.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(fragmentActivity:FragmentActivity,private val _pageNumber:Int)
     : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount():Int  = _pageNumber
        override fun createFragment(position: Int): Fragment {
            return MainFragment.newInstance()
        }

}