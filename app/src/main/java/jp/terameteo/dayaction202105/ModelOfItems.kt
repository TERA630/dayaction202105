package jp.terameteo.dayaction202105


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StoredItemEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var title: String = "unnamed",
    var reward: Int = 30,
    var category: String = "",
    var finishedHistory: String = ""
)

class TodayItemEntity(
    var title: String = "unnamed",
    var reward: Int = 30,
    var category: String = "",
    var shouldDoToday: Boolean = true,
    var isChecked: Boolean = false
)





