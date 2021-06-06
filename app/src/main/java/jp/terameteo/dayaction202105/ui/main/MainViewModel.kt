package jp.terameteo.dayaction202105.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import jp.terameteo.dayaction202105.R
import jp.terameteo.dayaction202105.StoredItemEntity
import jp.terameteo.dayaction202105.TodayItemEntity

class MainViewModel : ViewModel() {
    val  currentItems = mutableListOf<TodayItemEntity>()

    fun convertItemToString(StoredItemEntity: StoredItemEntity): String { // ItemEntity が変われば直す必要あり
        return StoredItemEntity.title + ";" + StoredItemEntity.reward + ";" + StoredItemEntity.category
    }

    fun makeDefaultItems(_context: Context) {
        val itemsString = _context.resources.getStringArray(R.array.default_item_list)
        val defaultItems = mutableListOf<StoredItemEntity>()
        for(i in itemsString.indices) {
            defaultItems.add(convertStringToItem(itemsString[i]))
        }
        currentItems.clear()
        for (i in defaultItems.indices) {
            val item = TodayItemEntity(
                defaultItems[i].title,defaultItems[i].reward,defaultItems[i].category,shouldDoToday = true,isChecked = false)
            currentItems.add(i,item)
        }
    }
    private fun convertStringToItem(_string: String): StoredItemEntity {
        val elementList = _string.split(";").toMutableList()
        for (i in elementList.indices) {
            if (elementList[i] == "") {
                elementList[i] = "0"
            }
        }
        return StoredItemEntity(
            0, title = elementList[0], reward = elementList[1].toInt(), category = elementList[2]
        )
    }

}
