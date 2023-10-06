package com.tenkovskaya.tetrisgame.models

import android.graphics.Point
import com.tenkovskaya.tetrisgame.consstant.Count
import java.util.Random


class Block private constructor(private val shapeIndex: Int, val color: BlockColor) {
    var number = 0
        private set
    var point = Point(Count.COLUMN_COUNT.value / 2, 0)
        private set

    init {
        point = Point(Count.COLUMN_COUNT.value / 2, 0)
    }

    fun setState(frame: Int, position: Point) {
        number = frame
        this.point = position
    }

    fun getShape(frameNumber: Int): Array<ByteArray> {
        return ShapeFrameFactory.values()[shapeIndex].frame(frameNumber).arrays()
    }

    val frameCount: Int
        get() = ShapeFrameFactory.values()[shapeIndex].frameCount


    companion object {
        fun createBlock(): Block {
            val random = Random()
            val factoryIndex = random.nextInt(ShapeFrameFactory.values().size)
            val color = BlockColor.values()[random.nextInt(BlockColor.values().size)]
            val block = Block(factoryIndex, color)
            block.point.x -= ShapeFrameFactory.values()[factoryIndex].startPosition
            return block
        }

        fun getColor(value: Byte): Int {
            return BlockColor.values().firstOrNull { it.staticValue == value }?.rgbValue ?: -1
        }
    }
}
