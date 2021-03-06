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

const val ARG_POSITION = "positionOfThisFragment"

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding:FragmentMainBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val flexBoxLayoutManager = FlexboxLayoutManager(this.context).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
        }
        val page = this.arguments?.getInt(ARG_POSITION) ?:0
        binding.dataShowing.text = viewModel.dateJpList[page]
        binding.firstPageList.layoutManager = flexBoxLayoutManager
        val adapter = MainListAdaptor(viewModel = viewModel,page)

        viewModel.liveList.observe(viewLifecycleOwner){
            adapter.submitList(it)
            binding.firstPageList.adapter = adapter
        }
        return binding.root
    }
    companion object {
        @JvmStatic
        fun newInstance(position: Int): MainFragment {
            val newFragment = MainFragment()
            newFragment.arguments = Bundle().apply {
                putInt(ARG_POSITION,position)
            }
            return newFragment
        }
    }
}

// FragmentはModelを直接さわらない
// また､LivedataのValueはデーターベースからの読み込みが終わるまではNullableであり､
// Fragment生成時に値を直接読み込まない方がよい  ×  View. Text = Livedata.value
// LivedataはObserveされていないと更新されないためずっとNullが返ってくる｡ (かなり長いことバグの温床でした)
// Livedataが変更されてもこのままだと表示が更新されない｡
