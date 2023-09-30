package com.hfad.tasks

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract val taskDao : TaskDao
    companion object {
        // dung companion object de goi ham getInstance() o duoi
        // ma khong dung can tao ra doi tuong TaskDatabase
        // vi companion object duoc goi khi chua tao ra doi tuong instance TaskDatabase
        // Goi ham bang cach dung ten class TaskDatabase.getInstance de goi
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        // getInstance tra ra 1 instance database dung chung cho tat ca cac object TaskDatabase
        // neu chua co thi build ra 1 database
        fun getInstance(context: Context): TaskDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) { // neu chua co database thi tao ra
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "task_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}