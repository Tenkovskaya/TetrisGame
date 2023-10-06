package com.tenkovskaya.tetrisgame.models


class NewFrame(private val width: Int) {
    private val data: ArrayList<ByteArray> = ArrayList()

    fun arrays(): Array<ByteArray> {
        return data.toTypedArray()
    }

    fun frame(byteStr: String): NewFrame {
        val row = ByteArray(byteStr.length) { index -> "${byteStr[index]}".toByte() }
        data.add(row)
        return this
    }

}
