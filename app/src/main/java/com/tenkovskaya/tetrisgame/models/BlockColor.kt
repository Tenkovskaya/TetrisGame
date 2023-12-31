package com.tenkovskaya.tetrisgame.models

import android.graphics.Color

enum class BlockColor(val rgbValue: Int, val staticValue: Byte) {
    PINK(Color.rgb(255, 105, 180), 2.toByte()),
    GREEN(Color.rgb(0, 128, 0), 3.toByte()),
    ORANGE(Color.rgb(255, 140, 0), 4.toByte()),
    YELLOW(Color.rgb(255, 255, 0), 5.toByte()),
    CYAN(Color.rgb(0, 255, 255), 6.toByte())
}