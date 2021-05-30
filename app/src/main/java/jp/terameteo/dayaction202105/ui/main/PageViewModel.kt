package jp.terameteo.dayaction202105.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import android.content.Context
import androidx.room.Room
import jp.terameteo.dayaction202105.AppDatabase
import jp.terameteo.dayaction202105.ItemEntity
import jp.terameteo.dayaction202105.R

class PageViewModel : ViewModel() {
    val rewardIGot:Int = 0
    var currentDateStr = "2021/5/30"
    val currentItems = mutableListOf<ItemEntity>()
    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }
    fun makeDefaultItems(_context: Context){
        val itemsString = _context.resources.getStringArray(R.array.default_item_list)
        val defaultItems = mutableListOf<ItemEntity>()
        for(i in itemsString.indices){
            defaultItems.add(convertStringToItem(itemsString[i]))
        }
        currentItems.clear()
        currentItems.addAll(defaultItems)
    }
    fun loadItems(_context:Context){
        val appDatabase = Room.databaseBuilder(_context,AppDatabase::class.java,"database-name")
            .build()
        val userDao = appDatabase.userDao()
    }
    fun setIndex(index: Int) {
        _index.value = index
    }

    fun convertItemToString(itemEntity: ItemEntity): String { // ItemEntity が変われば直す必要あり
        return itemEntity.title + ";" + itemEntity.reward + ";" + itemEntity.category
    }
    fun convertStringToItem(_string: String): ItemEntity {
        val elementList = _string.split(";").toMutableList()
        for (i in elementList.indices) {
            if (elementList[i] == "") {
                elementList[i] = "0"
            }
        }
        return ItemEntity(
            0, title = elementList[0], reward = elementList[1].toInt(), category = elementList[2]
        )
    }

}
