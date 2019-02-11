package id.ac.akakom.mobile.todo.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.akakom.mobile.todo.data.model.ToDo

@Dao
interface ToDoDao {

    @Query("SELECT * FROM tasks")
    fun getAllItems(): LiveData<List<ToDo>>

    @Query("SELECT * FROM tasks WHERE id= :id")
    fun getItem(id: Int):LiveData<ToDo>

    @Delete
    fun delete(vararg todo: ToDo)

    @Insert
    fun insert(vararg todo: ToDo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg todo: ToDo)
}