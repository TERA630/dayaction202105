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
import jp.terameteo.dayaction202105.valueOrZero

const val ARG_POSITION = "argumentPosition"

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

  //    binding.dataShowing.text = pageViewModel.currentDateJp.value
        val positionNow = pageViewModel.currentPagePosition.valueOrZero()
        val dateEn = pageViewModel.currentDateEn.value
        pageViewModel.currentDateEn.observe(viewLifecycleOwner){
            Log.i("fragment","position is $positionNow and dateEn $it")
        }
        pageViewModel.currentDateJp.observe(
            viewLifecycleOwner, {
                binding.dataShowing.text = it
                Log.i("fragment","position is $positionNow and dateJp $dateEn")}
        )

        binding.firstPageList.apply {
            layoutManager = flexBoxLayoutManager
            adapter =
                MainListAdaptor(viewLifecycleOwner = viewLifecycleOwner, viewModel = pageViewModel)
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