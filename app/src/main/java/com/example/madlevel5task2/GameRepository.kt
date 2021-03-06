package com.example.madlevel5task2

import android.content.Context

class GameRepository(context: Context) {

    private var gameDao: GameDao

    init {
        val gameRoomDatabase = GameRoomDatabase.getDatabase(context)
        gameDao = gameRoomDatabase!!.gameDao()
    }

    fun getAllGames(): List<Game> {
        return gameDao.getAllGames()
//        return gameDao.getAllGames() ?: MutableLiveData(emptyList())
    }

    fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }
}