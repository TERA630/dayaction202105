package jp.terameteo.dayaction202105.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainFragmentStateAdapter(fragmentActivity:FragmentActivity)
     : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount():Int  = 10
        override fun createFragment(position: Int): Fragment {
            return MainFragment.newInstance(position)
        }

}

