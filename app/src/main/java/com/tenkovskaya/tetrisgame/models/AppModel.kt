package com.tenkovskaya.tetrisgame.models

import android.graphics.Point
import com.tenkovskaya.tetrisgame.Preferences
import com.tenkovskaya.tetrisgame.consstant.Count
import com.tenkovskaya.tetrisgame.consstant.EmptyEphemeral

class AppModel {
    var score: Int = 0
    private var preferences: Preferences? = null
    var block: Block? = null
    var state: Statuses = Statuses.AWAITING_START

    private val arrayOfByteArrays: Array<ByteArray> = Array(
        Count.ROW_COUNT.value
    ) { ByteArray(Count.COLUMN_COUNT.value) }

    fun preferences(preferences: Preferences?) {
        this.preferences = preferences
    }

    fun cellStatus(row: Int, column: Int): Byte {
        return arrayOfByteArrays[row][column]
    }

    private fun status(row: Int, column: Int, status: Byte) {
        arrayOfByteArrays[row][column] = status
    }

    fun active(): Boolean {
        return state == Statuses.ACTIVE
    }

    fun start(): Boolean {
        return state == Statuses.AWAITING_START
    }

    fun gameOver(): Boolean {
        return state == Statuses.OVER
    }

    private fun score() {
        score += 10
        if (score > preferences?.getHighScore() ?: 0) {
            preferences?.saveHighScore(score)
        }
    }

    private fun nextBlock() {
        block = Block.createBlock()
    }

    private fun translation(position: Point, shape: Array<ByteArray>): Boolean {
        return if (position.y < 0 || position.x < 0) {
            false
        } else if (position.y + shape.size > Count.ROW_COUNT.value) {
            false
        } else if (position.x + shape[0].size > Count.COLUMN_COUNT.value) {
            false
        } else {
            for (i in shape.indices) {
                for (j in 0 until shape[i].size) {
                    val y = position.y + i
                    val x = position.x + j
                    if (EmptyEphemeral.EMPTY.value != shape[i][j] &&
                        EmptyEphemeral.EMPTY.value != arrayOfByteArrays[y][x]
                    ) {
                        return false
                    }
                }
            }
            true
        }
    }

    private fun valid(position: Point, frameNumber: Int?): Boolean {
        val shape: Array<ByteArray>? = block?.getShape(frameNumber as Int)
        return translation(position, shape as Array<ByteArray>)
    }

    fun generateField(action: String) {
        if (active()) {
            field1()
            var number: Int? = block?.number
            val position: Point? = Point()
            position?.x = block?.point?.x
            position?.y = block?.point?.y

            when (action) {
                Motions.LEFT.name -> {
                    position?.x = block?.point?.x?.minus(1)
                }

                Motions.RIGHT.name -> {
                    position?.x = block?.point?.x?.plus(1)
                }

                Motions.DOWN.name -> {
                    position?.y = block?.point?.y?.plus(1)
                }

                Motions.ROTATE.name -> {
                    number = number?.plus(1)
                    if (number != null) {
                        if (number >= block?.frameCount as Int) {
                            number = 0
                        }
                    }
                }
            }

            if (!valid(position as Point, number)) {
                block(block?.point as Point, block?.number as Int)
                if (Motions.DOWN.name == action) {
                    score()
                    data()
                    field()
                    nextBlock()
                    if (!possible()) {
                        state = Statuses.OVER
                        block = null
                        field1(false)
                    }
                }
            } else {
                if (number != null) {
                    block(position, number)
                    block?.setState(number, position)
                }
            }
        }
    }

    private fun field1(ephemeralCellsOnly: Boolean = true) {
        for (i in 0 until Count.ROW_COUNT.value) {
            (0 until Count.COLUMN_COUNT.value).filter {
                !ephemeralCellsOnly || arrayOfByteArrays[i][it] == EmptyEphemeral.EPHEMERAL.value
            }.forEach { arrayOfByteArrays[i][it] = EmptyEphemeral.EMPTY.value }
        }
    }

    private fun data() {
        for (i in arrayOfByteArrays.indices) {
            for (j in 0 until arrayOfByteArrays[i].size) {
                var status = cellStatus(i, j)
                if (status == EmptyEphemeral.EPHEMERAL.value) {
                    status = block?.color?.staticValue!!
                    status(i, j, status)
                }
            }
        }
    }

    private fun field() {
        for (i in arrayOfByteArrays.indices) {
            var cells = 0
            for (j in 0 until arrayOfByteArrays[i].size) {
                val status = cellStatus(i, j)
                val isEmpty = EmptyEphemeral.EMPTY.value == status
                if (isEmpty) cells++
            }
            if (cells == 0) rows(i)
        }
    }

    private fun block(position: Point, frameNumber: Int) {
        synchronized(arrayOfByteArrays) {
            val arrays: Array<ByteArray>? = block?.getShape(frameNumber)
            if (arrays != null) {
                for (i in arrays.indices) {
                    for (j in 0 until arrays[i].size) {
                        val y = position.y + i
                        val x = position.x + j
                        if (EmptyEphemeral.EMPTY.value != arrays[i][j]) {
                            arrayOfByteArrays[y][x] = arrays[i][j]
                        }
                    }
                }
            }
        }
    }

    private fun possible(): Boolean {
        if (!valid(block?.point as Point, block?.number)) {
            return false
        }
        return true
    }

    private fun rows(nToRow: Int) {
        if (nToRow > 0) {
            for (j in nToRow - 1 downTo 0) {
                for (m in 0 until arrayOfByteArrays[j].size) {
                    status(j + 1, m, cellStatus(j, m))
                }
            }
        }

        for (j in 0 until arrayOfByteArrays[0].size) {
            status(0, j, EmptyEphemeral.EMPTY.value)
        }
    }

    fun game() {
        if (!active()) {
            state = Statuses.ACTIVE
            nextBlock()
        }
    }

    fun restartGame() {
        model()
        game()
    }

    fun endGame() {
        score = 0
        state = Statuses.OVER
    }

    private fun model() {
        field1(false)
        state = Statuses.AWAITING_START
        score = 0
    }

}