package jp.terameteo.dayaction202105.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.flexbox.*
import jp.terameteo.dayaction202105.MainViewModel
import jp.terameteo.dayaction202105.databinding.FragmentMainBinding

const val ARG_POSITION = "positionOfThisFragment"

class MainFragment : Fragment() {
    private val pageViewModel: MainViewModel by activityViewModels()
    private lateinit var binding:FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val flexBoxLayoutManager = FlexboxLayoutManager(this.context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
        }
        val positionOfArgumentOfFragment = this.arguments?.getInt(ARG_POSITION) ?:0

        pageViewModel.currentDateEn.observe(viewLifecycleOwner){
            Log.i("fragment","Pos is $positionOfArgumentOfFragment from list is ${pageViewModel.dateEnList[positionOfArgumentOfFragment]}")
        }
        binding.dataShowing.text = pageViewModel.dateJpList[positionOfArgumentOfFragment]

        binding.firstPageList.apply {
            layoutManager = flexBoxLayoutManager
            adapter =
                MainListAdaptor(viewLifecycleOwner = viewLifecycleOwner, viewModel = pageViewModel,positionOfArgumentOfFragment)
        }
        return binding.root
    }
    companion object {
        @JvmStatic
        fun newInstance(position: Int): MainFragment {
            val newFragment = MainFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION,position)
            newFragment.arguments = args
            return newFragment
        }
    }
}

// FragmentはModelを直接さわらない
//