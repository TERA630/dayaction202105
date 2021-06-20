package jp.terameteo.dayaction202105.model

import androidx.room.*

@Dao
interface ItemCollectionDAO {
    /** 全データ取得 */

    @Query("SELECT * FROM collection_item")
     fun getAll(): List<ItemEntity>
    /** データ更新 */
    @Update
     fun update(item: ItemEntity)
    /** データ追加 */
    @Insert (onConflict = OnConflictStrategy.ABORT)
     fun insert(item: ItemEntity)
    /** データ削除 */
    @Delete
     fun delete(item: ItemEntity)

}
@Database(entities = [ItemEntity::class], version = 1) // 使うentityのクラスを渡す｡
abstract class ItemCollectionDB : RoomDatabase() {
    abstract fun itemCollectionDAO(): ItemCollectionDAO // 上記Interfaceの抽象メソッドを含む
}

@Entity (tableName = "collection_item")
data class ItemEntity(
    @PrimaryKey @ColumnInfo(name = "id", index = true) var id:Int = 0,
    var title: String = "unnamed",
    var reward: Int = 30,
    var category: String = "",
    var shouldDoToday: Boolean = true,
    var finishedHistory: String = ""
)