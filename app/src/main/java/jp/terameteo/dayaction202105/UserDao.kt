package jp.terameteo.dayaction202105

import androidx.room.*

@Dao
 interface UserDao {
    @Insert
    fun insert(user: StoredItemEntity)

    @Update
    fun update(user: StoredItemEntity)

    @Delete
    fun delete(user: StoredItemEntity)

    @Query("select * from StoredItemEntity")
    fun selectAll(): List<StoredItemEntity>
}

@Database(entities = [StoredItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}