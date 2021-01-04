package com.example.madlevel5task2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {
    @Query("SELECT * FROM gameBacklogTable")
    fun getAllGames(): List<Game>
//    fun getAllGames(): LiveData<List<Game>>

    @Insert
    fun insertGame(reminder: Game)

    @Delete
    fun deleteGame(reminder: Game)

    @Query("DELETE FROM gameBacklogTable")
    fun nukeTable()
}