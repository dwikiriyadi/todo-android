package id.ac.akakom.mobile.todo.data.model

import androidx.room.*

import id.ac.akakom.mobile.todo.utility.*
import java.util.*

@Entity(tableName = "tasks")
data class ToDo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "task_name")
    var taskName: String,
    var status: Boolean = false,
    @ColumnInfo(name = "reminder")
    var reminder: Boolean = false,
    @ColumnInfo(name = "reminder_date")
    var reminderDate: Calendar? = Calendar.getInstance(),
    @ColumnInfo(name = "reminder_repeat")
    var reminderRepeat: String? = null
)