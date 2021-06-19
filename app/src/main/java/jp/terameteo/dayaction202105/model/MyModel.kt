package jp.terameteo.dayaction202105.model

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.text.format.DateFormat
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.room.PrimaryKey
import jp.terameteo.dayaction202105.R
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

const val ERROR_TITLE = "error title"
const val ERROR_CATEGORY = "error category"
const val REWARD_HISTORY = "rewardHistory"

class MyModel {
    fun getDayStringJp(backDate:Int): String {
        val local = Locale.JAPAN
        val pattern = DateFormat.getBestDateTimePattern(local, "YYYYEEEMMMd")
        val date = LocalDate.now().minusDays(backDate.toLong())
        Log.i("model","currentDate is ${LocalDate.now()}")
        val javaUtilDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
        return SimpleDateFormat(pattern, local).format(javaUtilDate)
    } // 0：本日　1～：backDate日前を返す｡
    fun getDayStringEn (backDate: Int):String{
        val date = LocalDate.now().minusDays(backDate.toLong())
        val javaUtilDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
        return  SimpleDateFormat("yyyy/M/dd",Locale.ENGLISH).format(javaUtilDate)
    }
    fun getItemsOfDay(_context: Context, dateStr: String): List<TodayItemEntity> {
        val items = parseItemFromResource(_context)
        return MutableList(items.size) { i ->
            TodayItemEntity(
                items[i].id, items[i].title, items[i].reward, items[i].category,
                isChecked = (dateStr.toRegex().containsMatchIn(items[i].finishedHistory)),
                shouldDoToday = true, finishedHistory = items[i].finishedHistory
            )
        }
    }
    private fun parseItemFromResource(_context: Context): List<StoredItemEntity> {
        val itemsFromResource = _context.resources.getStringArray(R.array.default_item_list)
        return List(itemsFromResource.size) { index ->
            convertStringToStoredItem(index, itemsFromResource[index])
        }
    }
    private fun convertItemToString(StoredItemEntity: StoredItemEntity): String { // ItemEntity が変われば直す必要あり
        return StoredItemEntity.title + ";" + StoredItemEntity.reward + ";" + StoredItemEntity.category + ";" + StoredItemEntity.finishedHistory
    }
    private fun convertStringToStoredItem(id:Int, _string: String): StoredItemEntity {
        // string = "title ; reward ; category ; finishedHistory" を与えられ､
        // storedItem (title,reward,category,finishedHistory )を返す｡
        val elementList = _string.split(";").toMutableList()

        // 文字列が規則に従っているか
        val title = if(elementList[0].isBlank()) ERROR_TITLE else elementList[0].trim()
        val reward = if(elementList[1].isDigitsOnly())  elementList[1].toInt() else 0
        val category = if(elementList[2].isBlank()) ERROR_CATEGORY else elementList[2].trim()

        return if (elementList.size < 4) {
            // historyが無い場合
            StoredItemEntity(id, title,reward,category,"")
        } else {
            // history がある場合
            if(elementList[3].matches("(20[0-9]{2}/([1-9]|1[0-2])/([1-9]|[12][0-9]|3[01]),?)+".toRegex())) {
                // 年：2000-2099 /月： 1～9 or 10～12/ 日： 1～9　or　10～29　or　30,31の要素が一つでもあればマッチ
                StoredItemEntity(id, title,reward,category,elementList[3])
            } else {
                // マッチしなければ空文字列をHistoryに返しておく
                StoredItemEntity(id,title,reward,category,"")
            }
        }
    }
    fun makeCategoryList( _itemList:List<TodayItemEntity>) : List<String>{
        val categoryList = List(_itemList.size){index-> _itemList[index].category}
        return categoryList.distinct()
    }
    fun loadRewardFromPreference(_context: Context):Int {
        val preferences = _context.getSharedPreferences(REWARD_HISTORY, Context.MODE_PRIVATE)
        return preferences?.getInt(REWARD_HISTORY, 0) ?: 0
    }
    fun saveRewardToPreference(reward: Int,_context: Context){
        val preferenceEditor = _context.getSharedPreferences(REWARD_HISTORY, Context.MODE_PRIVATE).edit()
        preferenceEditor.putInt(REWARD_HISTORY, reward)
        preferenceEditor.apply()
    }
    fun appendDateToItem(itemEntity: TodayItemEntity,dateStr:String) {
        val dateList = itemEntity.finishedHistory.split(",").toMutableList()
        if(dateStr.matches("20[0-9]{2}/([1-9]|1[0-2])/([1-9]|[12][0-9]|3[01])".toRegex())){
            dateList.add(dateStr)
            dateList.sort()
            val newDateList = dateList.joinToString(",")
            itemEntity.finishedHistory = newDateList
        } else {
            Log.w("model","date Appending was fail")
        }
    }
    fun deleteDateFromItem(itemEntity:TodayItemEntity,dateStr: String){
        val dateList = itemEntity.finishedHistory.split(",").toMutableList()
        if(dateList.contains(dateStr)){
            dateList.remove(dateStr)
            val newDateList = dateList.joinToString (",")
            itemEntity.finishedHistory = newDateList
        } else {
            Log.w("model","date deleting was fail")
        }

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
    var isChecked: Boolean = false,
    var finishedHistory: String = ""
)