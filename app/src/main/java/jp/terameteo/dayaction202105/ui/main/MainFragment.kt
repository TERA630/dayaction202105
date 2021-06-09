package jp.terameteo.dayaction202105.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.flexbox.*
import jp.terameteo.dayaction202105.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val pageViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView( // これから画面を描画する
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root



        val flexBoxLayoutManager = FlexboxLayoutManager(this.context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
        }
        val firstPageListView = binding.firstPageList
        firstPageListView.layoutManager = flexBoxLayoutManager

        val thisPageListAdaptor  = MainListAdaptor(viewLifecycleOwner = viewLifecycleOwner,viewModel = pageViewModel)
        firstPageListView.adapter = thisPageListAdaptor
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
        _binding = null
    }
}

// FragmentはModelを直接さわらない
//