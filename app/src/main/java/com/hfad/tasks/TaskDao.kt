package com.hfad.tasks

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface TaskDao {
    @Insert
    fun insert(task: Task)
}