package id.ac.akakom.mobile.todo.data

import android.content.Context
import androidx.room.*
import id.ac.akakom.mobile.todo.data.dao.ToDoDao
import id.ac.akakom.mobile.todo.data.model.ToDo
import id.ac.akakom.mobile.todo.utility.Converters

@Database(entities = [ToDo::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun todoDao(): ToDoDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it}
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "database-todo")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}