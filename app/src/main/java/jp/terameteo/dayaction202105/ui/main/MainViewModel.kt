package jp.terameteo.dayaction202105.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import jp.terameteo.dayaction202105.AppDatabase
import jp.terameteo.dayaction202105.R
import jp.terameteo.dayaction202105.StoredItemEntity

class MainViewModel : ViewModel() {
    val rewardIGot: Int = 0
    val currentItems = mutableListOf<StoredItemEntity>()
    private val _index = MutableLiveData<Int>()

    fun makeDefaultItems(_context: Context) {
        val itemsString = _context.resources.getStringArray(R.array.default_item_list)
        val defaultItems = mutableListOf<StoredItemEntity>()
        for (i in itemsString.indices) {
            defaultItems.add(convertStringToItem(itemsString[i]))
        }
         currentItems.clear()
         currentItems.addAll(defaultItems)

    }

    fun loadItems(_context: Context) {
        val appDatabase = Room.databaseBuilder(_context, AppDatabase::class.java, "AppDatabase")
            .build()
        val userDao = appDatabase.userDao()
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun convertItemToString(StoredItemEntity: StoredItemEntity): String { // ItemEntity が変われば直す必要あり
        return StoredItemEntity.title + ";" + StoredItemEntity.reward + ";" + StoredItemEntity.category
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
