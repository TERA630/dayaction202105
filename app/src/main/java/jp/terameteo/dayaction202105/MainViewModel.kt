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
    private val myModel: MyModel by lazy { MyModel() }
    val liveList = MutableLiveData<List<ItemEntity>>()
    val dateJpList = MutableList(10){"1970年1月1日(木)"}
    val dateEnList = MutableList(10){"1970/1/1"}
    val currentReward:MutableLiveData<Int> = MutableLiveData(0)
    val currentRewardStr = MediatorLiveData<String>()
    val currentCategory = MediatorLiveData<List<String>>()

    fun initialize(_context:Context) {
        // TODO 後でROOMからデータを取れる様にする
        myModel.initializeDB(_context)
        for (i in 0..9) {
            dateEnList[i] = myModel.getDayStringEn(9 - i)
        }
        for (i in 0..9) {
            dateJpList[i] = myModel.getDayStringJp(9 - i)
        }
        currentReward.postValue(myModel.loadRewardFromPreference(_context))
        currentRewardStr.addSource(currentReward) { value -> currentRewardStr.postValue("$value　円") }
        viewModelScope.launch {
            val list = myModel.makeItemList(_context )
            liveList.postValue(list)
        }
        currentCategory.addSource(liveList){ value ->
            val list = myModel.makeCategoryList(value)
            currentCategory.postValue(list)
        }

    }

    fun stateSave(_context: Context) {
        val reward = currentReward.value ?:0
        myModel.saveRewardToPreference(reward,_context)
        val list = List(liveList.value?.size ?:0 ){
            index -> liveList.safetyGet(index)
        }
        viewModelScope.launch {
            for(i in list.indices) {
                myModel.insertItem(list[i])
            }
        }
    }

    // アイテムの履歴から､その日完了していたかどうかを返す｡  →View側ではそれに応じて表示内容を変える 本当はModelに入れとくものなんだが｡
    fun isItemDone(item: ItemEntity, dateStr: String): Boolean { // Str yyyy/mm/dd
        return dateStr.toRegex().containsMatchIn(item.finishedHistory)
    }

    // クリックでその日の完了/未完了を切り替える｡
    fun flipItemHistory(item:ItemEntity,page:Int){
        val currentValue =  currentReward.valueOrZero()
        if ( isItemDone(item,dateEnList[page])) {
            // アイテムがチェック済み チェックをはずす
            myModel.deleteDateFromItem(item,dateEnList[page])
            val newValue = currentValue - item.reward
            currentReward.postValue(newValue)
        } else {
            myModel.appendDateToItem(item,dateEnList[page])
            val newValue = currentValue + item.reward
            currentReward.postValue(newValue)
        }
    }

}

 fun MutableLiveData<Int>.valueOrZero() : Int{
      return this.value ?: 0
 }
fun MutableLiveData<List<ItemEntity>>.safetyGet(position:Int): ItemEntity {
    val list = this.value
    return if (list.isNullOrEmpty()) {
        ItemEntity()
    } else {
        list[position]
    }
}
// ViewModel
// Activity再生成や回転で破棄されないClass(ViewModelLifeCycle)
//　各Activity固有｡ 同じActivityのFragmentでは共有される｡
//　値の保持をLivedataで行うのが主流｡
//　Model-> ViewModel　ModelからUIの描画(Binding)に必要な情報に変換し保持する｡
//　ActivityやFragmentはLiveDataをObserveして変更があればUI反映 or DataBinding使用｡ VMはViewへの参照は持つべきでない｡
//　Context の参照を保持するべきでない｡
//　ユーザーのViewへのActionを受け取り､Modelに通知する｡　Commands
//　ViewがModelのメンバを直接操作するのは推奨されない｡
