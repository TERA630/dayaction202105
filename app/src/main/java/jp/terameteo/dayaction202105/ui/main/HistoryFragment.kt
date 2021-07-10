package jp.terameteo.dayaction202105.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import jp.terameteo.dayaction202105.MainViewModel
import jp.terameteo.dayaction202105.databinding.FragmentHistoryBinding

class HistoryFragment:Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.historyGrid.layoutManager = GridLayoutManager(binding.root.context,8,GridLayoutManager.HORIZONTAL,false)
        binding.historyGrid.setHasFixedSize(true)
        binding.historyGrid.adapter = HistoryAdaptor(viewModel)


        return binding.root
    }
    companion object {
        @JvmStatic
        fun newInstance(): HistoryFragment {
            val newFragment = HistoryFragment()
            return newFragment
        }
    }
}