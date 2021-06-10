package jp.terameteo.dayaction202105.model

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.text.format.DateFormat
import androidx.core.text.isDigitsOnly
import androidx.room.PrimaryKey
import jp.terameteo.dayaction202105.R
import java.util.*

const val ERROR_TITLE = "error title"
const val ERROR_CATEGORY = "error category"

const val REWARD_HISTORY = "rewardHistory"

class MyModel {
    fun getTodayString(): String {
        val local = Locale.JAPAN
        val pattern = DateFormat.getBestDateTimePattern(local, "YYYYEEEMMMd")
        val dateFormat = SimpleDateFormat(pattern, local)
        return dateFormat.format(System.currentTimeMillis())
    }

    fun getItemsFromResource (_context: Context) :List<TodayItemEntity> {
        val itemsFromResource = _context.resources.getStringArray(R.array.default_item_list)

        val items = List(itemsFromResource.size) { index ->
            convertStringToItem(index, itemsFromResource[index]) //　アイテム作成ラムダ
        }
        val newItems = MutableList(items.size) { i ->
            TodayItemEntity(items[i].id,items[i].title,items[i].reward,items[i].category,
            shouldDoToday = true,isChecked = false)
        }
        return newItems
    }
    private fun convertItemToString(StoredItemEntity: StoredItemEntity): String { // ItemEntity が変われば直す必要あり
        return StoredItemEntity.title + ";" + StoredItemEntity.reward + ";" + StoredItemEntity.category
    }
    private fun convertStringToItem(id:Int,_string: String): StoredItemEntity {
        // string = "title ; reward ; category ; " を与えられ､
        // storedItem objectを返す｡
        val elementList = _string.split(";").toMutableList()

        // 文字列が規則に従っているか
        val title = if(elementList[0].isBlank()) {
            ERROR_TITLE
            } else {
            elementList[0].trim()
            }
        val reward = if(elementList[1].isDigitsOnly()) {
            elementList[1].toInt()
            } else {
            0
        }
        val category = if(elementList[2].isBlank()){
            ERROR_CATEGORY
        }else{
            elementList[2].trim()
        }
        return StoredItemEntity(id, title,reward,category)
    }
    fun loadRewardFromPreference(_context: Context):Int {
        val preferences = _context.getSharedPreferences(REWARD_HISTORY, Context.MODE_PRIVATE)
        return preferences?.getInt(REWARD_HISTORY, 0) ?: 0
    }
    fun saveRewardToPreference(_context: Context){
        val preferenceEditor = _context.getSharedPreferences(REWARD_HISTORY, Context.MODE_PRIVATE).edit()
        preferenceEditor.putInt(REWARD_HISTORY, 0)
        preferenceEditor.apply()
    }



}



data class StoredItemEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var title: String = "unnamed",
    var reward: Int = 30,
    var category: String = "",
    var finishedHistory: String = ""
)

data class TodayItemEntity(
    var id:Int = 0,
    var title: String = "unnamed",
    var reward: Int = 30,
    var category: String = "",
    var shouldDoToday: Boolean = true,
    var isChecked: Boolean = false
)





