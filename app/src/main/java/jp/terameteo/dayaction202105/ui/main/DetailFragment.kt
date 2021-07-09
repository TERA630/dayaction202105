package jp.terameteo.dayaction202105.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import jp.terameteo.dayaction202105.DETAIL_WINDOW
import jp.terameteo.dayaction202105.MainViewModel
import jp.terameteo.dayaction202105.R
import jp.terameteo.dayaction202105.databinding.FragmentDetailBinding
import jp.terameteo.dayaction202105.model.ERROR_CATEGORY

const val ARG_ITEM_ID= "argumentItemId"
class DetailFragment :Fragment(){ // Fragmentはデフォルトのコンストラクタを使うことを要求されている｡
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding =  FragmentDetailBinding.inflate(inflater, container, false)
//        イベントハンドルの設定
        viewModel.currentCategory.observe(viewLifecycleOwner){
            val arrayAdapter = ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item)
            val categoryList = viewModel.currentCategory.value ?: listOf(ERROR_CATEGORY)
            for(i in categoryList.indices){
                arrayAdapter.add(categoryList[i])
            }
            binding.spinner.adapter = arrayAdapter
        }
        binding.detailCancelButton.setOnClickListener {
            navigateToMain()
        }
        binding.detailOkButton.setOnClickListener{
            val title = binding.editTitle.text.toString()
            val reward = binding.editReward.text.toString().toInt()
            val category = binding.spinner.selectedItem.toString()
            viewModel.appendItem(title,reward,category)
            navigateToMain()
        }
        binding.spinner.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if(hasFocus){ //　SpinnerのFocus時はソフトキーボードを隠す｡
                val imm = getSystemService(this.requireContext(),InputMethodManager::class.java) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
        return binding.root
    }
    private fun navigateToMain(){
        val transaction = parentFragmentManager.beginTransaction()
        parentFragmentManager.findFragmentByTag(DETAIL_WINDOW)?.let{
            transaction.hide(it)
            transaction.commit()
        }//このフラグメントを閉じる｡
    }
    companion object {
        @JvmStatic
        fun newInstance(position: Int): DetailFragment {
            val newFragment = DetailFragment()
            newFragment.arguments = Bundle().apply {// プロパティを設定した呼び出し元のインスタンスを返すapplyは自身がthis Alsoはit
                putInt(ARG_ITEM_ID,position)
            }
            return newFragment
        }
    }
}