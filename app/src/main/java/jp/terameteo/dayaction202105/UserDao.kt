package jp.terameteo.dayaction202105

import androidx.room.*

@Dao
 interface UserDao {
        @Insert
        fun insert(user : ItemEntity)

        @Update
        fun update(user : ItemEntity)

        @Delete
        fun delete(user : ItemEntity)

        @Query("select * from ItemEntity")
        fun selectAll():List<ItemEntity>
 }

@Database(entities = [ItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}