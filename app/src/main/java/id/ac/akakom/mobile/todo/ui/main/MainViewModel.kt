package id.ac.akakom.mobile.todo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import id.ac.akakom.mobile.todo.data.repository.ToDoRepository
import id.ac.akakom.mobile.todo.data.model.ToDo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class MainViewModel internal constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    val getAllItems: LiveData<List<ToDo>> = Transformations.map(toDoRepository.getAllItems()) { todos ->
        todos.filter { it.taskName.isNotEmpty() }
    }

    fun insert(toDo: ToDo) = scope.launch(Dispatchers.IO) { toDoRepository.insert(toDo) }

    fun delete(toDo: ToDo) = scope.launch(Dispatchers.IO) { toDoRepository.delete(toDo) }

    fun update(toDo: ToDo) = scope.launch(Dispatchers.IO) { toDoRepository.update(toDo) }

}
