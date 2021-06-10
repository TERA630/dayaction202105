package jp.terameteo.dayaction202105

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.terameteo.dayaction202105.model.MyModel
import jp.terameteo.dayaction202105.model.TodayItemEntity

class MainViewModel : ViewModel() {
    var currentDate = "19xx年xx月xx日"
    val currentItems = mutableListOf<TodayItemEntity>()
    val currentReward:MutableLiveData<Int> = MutableLiveData(0)
    val currentRewardStr = MediatorLiveData<String>()
    val currentCategory = emptyList<String>().toMutableList()

    private lateinit var myModel: MyModel

    fun initialize(_context:Context){
        // TODO 後でROOMからデータを取れる様にする
        myModel = MyModel()
        currentDate = myModel.getTodayString()
        currentItems.clear()
        currentItems.addAll(myModel.getItemsFromResource(_context))
        currentCategory.addAll(myModel.makeCategoryList(currentItems))
        currentReward.postValue(myModel.loadRewardFromPreference(_context))
        currentRewardStr.addSource(currentReward){
            value -> currentRewardStr.value = "$value　円"
        }


    }

    fun stateSave(_context: Context) {
        myModel.saveRewardToPreference(_context)
    }
}

// ViewModel 　Viewの状態保持
//　回転やアプリ切り替えなどでも破棄されない､Activityとは別のLifecycleを持つ｡
//　LiveDataを保持する｡
//　Modelのデータから､ViewにUIを描画する(Binding)ための情報を渡す｡
//　Activity､Fragment､Adapterのいずれにも渡せるはず｡
//　LiveDataを保持する｡　
// ActivityやFragmentはObserveして変更があればUI更新
//　AdapterはViewModelを参照している｡　Observeするならば･･
//　Viewへの参照を保持するべきでない｡
//　Context/Activityの参照を保持するべきでない｡
//　ViewへのActionを送信｡　Commands
//　アンチパターン　ViewがViewModelのメンバを直接操作
//　Model-> ViewModel　ModelからUIの描画に必要な情報に変換保持
//　ViewからのActionをModelに通知｡