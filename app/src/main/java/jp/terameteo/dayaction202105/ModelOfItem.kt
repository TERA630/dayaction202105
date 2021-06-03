package jp.terameteo.dayaction202105

import androidx.lifecycle.MutableLiveData
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity (
    @PrimaryKey(autoGenerate = true) var id:Int = 0,
    var title:String = "unnamed",
    var reward:Int = 30,
    var category:String = "",
    var finishedHistory:String = ""
)


