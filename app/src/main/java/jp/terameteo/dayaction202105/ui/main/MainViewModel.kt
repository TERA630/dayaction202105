package jp.terameteo.dayaction202105.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.terameteo.dayaction202105.model.MyModel
import jp.terameteo.dayaction202105.model.TodayItemEntity

class MainViewModel : ViewModel() {
    val currentItems = mutableListOf<TodayItemEntity>()
    lateinit var currentReward:MutableLiveData<Int>
    fun intialize(_context:Context){
        // TODO 後でROOMからデータを取れる様にする
        currentItems.clear()
        val myModel = MyModel()
        currentItems.addAll(myModel.getItemsFromResource(_context))
        val reward  = myModel.loadRewardFromPreference(_context)
        currentReward.value = reward
    }




}

// ViewModel 　Viewの状態保持
//　回転やアプリ切り替えなどでも破棄されない､Activityとは別のLifecycleを持つ｡
//　LiveDataを保持する｡
//　Modelのデータから､ViewにUIを描画する(Binding)ための情報を渡す｡
//　Activity､Fragment､Adapterのいずれにも渡せるはず｡
//　LiveDataを保持する｡　ActivityやFragmentはObserveして変更があればUI更新
//　AdapterはViewModelを参照している｡　Observeするならば･･
//　Viewへの参照を保持するべきでない｡
//　Context/Activityの参照を保持するべきでない｡
//　ViewへのActionを送信｡　Commands
//　アンチパターン　ViewがViewModelのメンバを直接操作
//　Model-> ViewModel　ModelからUIの描画に必要な情報に変換保持
//　ViewからのActionをModelに通知｡