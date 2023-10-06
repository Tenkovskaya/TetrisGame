package com.tenkovskaya.tetrisgame

import android.content.Context
import android.content.SharedPreferences
import com.tenkovskaya.tetrisgame.consstant.HighScore

class Preferences(context: Context) {
    private val data: SharedPreferences = context
        .getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)

    fun saveHighScore(highScore: Int) {
        data.edit().putInt(HighScore.HIGH_SCORE.name, highScore).apply()
    }

    fun getHighScore(): Int {
        return data.getInt(HighScore.HIGH_SCORE.name, 0)
    }
}