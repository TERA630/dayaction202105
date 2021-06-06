package jp.terameteo.dayaction202105.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

const val NUM_PAGES = 2

class SectionsPagerAdapter(fragmentActivity:FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int): Fragment {
            return MainFragment.newInstance()
        }
}