package com.tenkovskaya.tetrisgame.consstant

enum class HighScore {
    HIGH_SCORE
}
enum class EmptyEphemeral(val value: Byte) {
    EMPTY(0), EPHEMERAL(1)
}

enum class Count(val value: Int) {
    COLUMN_COUNT(10), ROW_COUNT(20);
}