package jp.terameteo.dayaction202105.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.flexbox.*
import jp.terameteo.dayaction202105.MainViewModel
import jp.terameteo.dayaction202105.databinding.FragmentMainBinding

const val ARG_POSITION = "argumentPosition"

class MainFragment : Fragment() {
    private val pageViewModel: MainViewModel by activityViewModels()
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root
        val flexBoxLayoutManager = FlexboxLayoutManager(this.context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
        }
        val currentPosition =  this.arguments?.getInt(ARG_POSITION) ?: 10
        val backDate = 10 - currentPosition
        binding.dataShowing.text = pageViewModel.getDayStrJpBefore(backDate)
        pageViewModel.checkItemsHistory(backDate)

        binding.firstPageList.apply {
            layoutManager = flexBoxLayoutManager
            adapter = MainListAdaptor(viewLifecycleOwner = viewLifecycleOwner,viewModel = pageViewModel)
        }

        return root
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