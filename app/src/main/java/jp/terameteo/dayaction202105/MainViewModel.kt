package jp.terameteo.dayaction202105

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.terameteo.dayaction202105.model.MyModel
import jp.terameteo.dayaction202105.model.TodayItemEntity

class MainViewModel : ViewModel() {
    val currentItems = mutableListOf<TodayItemEntity>()
    var currentDateJp = "19xx/12/31"
    var currentPagePosition = 10
    val currentReward:MutableLiveData<Int> = MutableLiveData(0)
    val currentRewardStr = MediatorLiveData<String>()
    val currentCategory = emptyList<String>().toMutableList()
    private lateinit var myModel: MyModel

    fun initialize(_context:Context){
        // TODO 後でROOMからデータを取れる様にする
        myModel = MyModel()
        currentItems.clear()
        currentDateJp = myModel.getDayStringJp(0)
        val targetDateEn = myModel.getDayString(0)
        currentItems.addAll(myModel.getItemsOfDay(_context,targetDateEn))
        currentCategory.addAll(myModel.makeCategoryList(currentItems))
        currentReward.postValue(myModel.loadRewardFromPreference(_context))
        currentRewardStr.addSource(currentReward){
            value -> currentRewardStr.value = "$value　円"
        }
    }
    fun getDayStrBefore(int: Int):String{
        return myModel.getDayStringJp(int)
    }
    fun stateSave(_context: Context) {
        val reward = currentReward.value ?:0
        myModel.saveRewardToPreference(reward,_context)
    }
}

// ViewModel 　Viewの状態保持
//　回転やアプリ切り替えなどでも破棄されない｡
//　LiveDataを保持する｡
//　Modelのデータから､ViewにUIを描画する(Binding)ための情報を渡す｡
// ActivityやFragmentはObserveして変更があればUI更新
//　View  / Context の参照を保持するべきでない｡
//　ViewへのActionを送信｡　Commands
//　アンチパターン　ViewがViewModelのメンバを直接操作
//　Model-> ViewModel　ModelからUIの描画に必要な情報に変換保持
//　ViewからのActionをModelに通知｡