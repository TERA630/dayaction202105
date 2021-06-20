package jp.terameteo.dayaction202105

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.terameteo.dayaction202105.model.ItemEntity
import jp.terameteo.dayaction202105.model.MyModel

class MainViewModel : ViewModel() {
    val currentItems = mutableListOf<ItemEntity>()
    val dateJpList = MutableList(10){"1970年1月1日(木)"}
    var currentDateEn  = MediatorLiveData<String>()
    val dateEnList = MutableList(10){"1970/1/1"}
    val currentReward:MutableLiveData<Int> = MutableLiveData(0)
    val currentRewardStr = MediatorLiveData<String>()
    val currentCategory = emptyList<String>().toMutableList()
    private lateinit var myModel: MyModel

    fun initialize(_context:Context){
        // TODO 後でROOMからデータを取れる様にする
        myModel = MyModel()
        for(i in 0..9){
            dateEnList[i] = myModel.getDayStringEn(9-i)
        }
        for(i in 0..9){
            dateJpList[i] = myModel.getDayStringJp(9-i)
        }
        currentReward.postValue(myModel.loadRewardFromPreference(_context))
        currentRewardStr.addSource(currentReward){
                value -> currentRewardStr.postValue("$value　円") }
        currentItems.clear()
        currentItems.addAll(myModel.makeItemListFromResource(_context))
        currentCategory.addAll(myModel.makeCategoryList(currentItems))

    }
    fun appendDateTo(item: ItemEntity, dateStr: String){
        myModel.appendDateToItem(item,dateStr)
    }
    fun removeDateFrom(item:ItemEntity, dateStr: String){
        myModel.deleteDateFromItem(item,dateStr)
    }
    fun stateSave(_context: Context) {
        val reward = currentReward.value ?:0
        myModel.saveRewardToPreference(reward,_context)
    }
    fun isItemDone(item: ItemEntity, dateStr: String): Boolean { // Str yyyy/mm/dd
        return dateStr.toRegex().containsMatchIn(item.finishedHistory)
    }
}

 fun MutableLiveData<Int>.valueOrZero() : Int{
      return this.value ?: 0
 }

// ViewModel
//　回転やアプリ切り替えなどでも破棄されない｡
// ActivityやFragmentはObserveして変更があればUI更新
//　View  / Context の参照を保持するべきでない｡
//　ViewへのActionを受け取る｡　Commands
//　アンチパターン　ViewがModelのメンバを直接操作
//　Model-> ViewModel　ModelからUIの描画(Binding)に必要な情報に変換し保持する｡
//　ViewからのActionをModelに通知｡