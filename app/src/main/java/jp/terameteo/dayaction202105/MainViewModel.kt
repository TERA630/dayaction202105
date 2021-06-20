package jp.terameteo.dayaction202105

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.terameteo.dayaction202105.model.ItemEntity
import jp.terameteo.dayaction202105.model.MyModel
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val currentItems = mutableListOf<ItemEntity>()
    val liveList = MutableLiveData<List<ItemEntity>>()
    val dateJpList = MutableList(10){"1970年1月1日(木)"}
    val dateEnList = MutableList(10){"1970/1/1"}
    val currentReward:MutableLiveData<Int> = MutableLiveData(0)
    val currentRewardStr = MediatorLiveData<String>()
    val currentCategory = emptyList<String>().toMutableList()
    private lateinit var myModel: MyModel

    fun initialize(_context:Context) {
        // TODO 後でROOMからデータを取れる様にする
        myModel = MyModel()
        myModel.initializeDB(_context)
        for (i in 0..9) {
            dateEnList[i] = myModel.getDayStringEn(9 - i)
        }
        for (i in 0..9) {
            dateJpList[i] = myModel.getDayStringJp(9 - i)
        }
        currentReward.postValue(myModel.loadRewardFromPreference(_context))
        currentRewardStr.addSource(currentReward) { value -> currentRewardStr.postValue("$value　円") }
        currentCategory.addAll(myModel.makeCategoryList(currentItems))


        viewModelScope.launch {
            val list = myModel.makeItemList(_context )
            currentItems.clear()
            currentItems.addAll(list)
            liveList.postValue(currentItems)
        }
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
        val list = List(liveList.value?.size ?:0 ){
            index -> liveList.safetyGet(index)
        }
        for(i in list.indices) {
            if (list[i].title == "making") continue
            viewModelScope.launch {
                myModel.insertItem(list[i])
            }
        }

    }
    fun isItemDone(item: ItemEntity, dateStr: String): Boolean { // Str yyyy/mm/dd
        return dateStr.toRegex().containsMatchIn(item.finishedHistory)
    }
}

 fun MutableLiveData<Int>.valueOrZero() : Int{
      return this.value ?: 0
 }
fun MutableLiveData<List<ItemEntity>>.safetyGet(position:Int): ItemEntity {
    val list = this.value
    return if (list.isNullOrEmpty()) {
        ItemEntity(title = "making..")
    } else {
        list[position]
    }
}
// ViewModel
//　回転やアプリ切り替えなどでも破棄されない｡
// ActivityやFragmentはObserveして変更があればUI更新
//　View  / Context の参照を保持するべきでない｡
//　ViewへのActionを受け取る｡　Commands
//　アンチパターン　ViewがModelのメンバを直接操作
//　Model-> ViewModel　ModelからUIの描画(Binding)に必要な情報に変換し保持する｡
//　ViewからのActionをModelに通知｡