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
        binding.firstPageList.apply {
            layoutManager = flexBoxLayoutManager
            adapter = MainListAdaptor(viewLifecycleOwner = viewLifecycleOwner,viewModel = pageViewModel)
        }
        return root
    }
    companion object {
        @JvmStatic
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}

// FragmentはModelを直接さわらない
//