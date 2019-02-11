package id.ac.akakom.mobile.todo.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import id.ac.akakom.mobile.todo.data.dao.ToDoDao
import id.ac.akakom.mobile.todo.data.model.ToDo

class ToDoRepository private constructor(private val toDoDao: ToDoDao) {

    fun getAllItems(): LiveData<List<ToDo>> = toDoDao.getAllItems()

    fun getItem(id: Int): LiveData<ToDo> = toDoDao.getItem(id)

    fun delete(toDo: ToDo) {
        toDoDao.delete(toDo)
    }

    fun update(toDo: ToDo) {
        toDoDao.update(toDo)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(toDo: ToDo) {
        toDoDao.insert(toDo)
    }

    companion object {
        @Volatile private var instance: ToDoRepository? = null

        fun getInstance(toDoDao: ToDoDao) = instance ?: synchronized(this) {
            instance ?: ToDoRepository(toDoDao).also { instance = it }
        }
    }
}