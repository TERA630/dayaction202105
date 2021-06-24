package jp.terameteo.dayaction202105.ui.main

import android.os.Bundle

const val ARG_ITEM_ID= "argumentItemId"
class DetailFragment {

    companion object {
        @JvmStatic
        fun newInstance(position: Int): MainFragment {
            val newFragment = MainFragment()
            val args = Bundle()
            args.putInt(ARG_ITEM_ID,position)
            newFragment.arguments = args
            return newFragment
        }
    }

}