package jp.terameteo.dayaction202105.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import jp.terameteo.dayaction202105.MainViewModel
import jp.terameteo.dayaction202105.R
import jp.terameteo.dayaction202105.databinding.FragmentDetailBinding

const val ARG_ITEM_ID= "argumentItemId"
class DetailFragment :Fragment(){
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val arrayAdapter = ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item)
        for(i in viewModel.currentCategory.indices){
            arrayAdapter.add(viewModel.currentCategory[i])
        }
        binding.spinner.adapter = arrayAdapter

        return super.onCreateView(inflater, container, savedInstanceState)
    }
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