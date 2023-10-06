package com.tenkovskaya.tetrisgame.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.tenkovskaya.tetrisgame.activity.GameActivity
import com.tenkovskaya.tetrisgame.consstant.Count
import com.tenkovskaya.tetrisgame.consstant.EmptyEphemeral
import com.tenkovskaya.tetrisgame.models.AppModel
import com.tenkovskaya.tetrisgame.models.Block
import com.tenkovskaya.tetrisgame.models.Motions
import com.tenkovskaya.tetrisgame.models.Statuses

class TView : View {

    private val paint = Paint()
    private var move1: Long = 0
    private var appModel: AppModel? = null
    private var gameActivity1: GameActivity? = null
    private val handler = ViewHandler(this)
    private var size: Dimension = Dimension(0, 0)
    private var offset: Dimension = Dimension(0, 0)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    private class ViewHandler(private val owner: TView) : Handler() {
        override fun handleMessage(message: Message) {
            if (message.what == 0) {
                if (owner.appModel != null) {
                    if (owner.appModel!!.gameOver()) {
                        owner.appModel?.endGame()
                        Toast.makeText(owner.gameActivity1, "Game over", Toast.LENGTH_LONG).show()
                    }
                    if (owner.appModel!!.active()) {
                        owner.commandWithDelay(Motions.DOWN)
                    }
                }
            }
        }

        fun sleep(delay: Long) {
            removeMessages(0)
            sendMessageDelayed(obtainMessage(0), delay)
        }
    }

    private data class Dimension(val width: Int, val height: Int)

    fun model(model: AppModel) {
        this.appModel = model
    }

    fun activity(GameActivity: GameActivity) {
        this.gameActivity1 = GameActivity
    }

    fun command(move: Motions) {
        if (null != appModel && appModel?.state == Statuses.ACTIVE) {
            if (Motions.DOWN == move) {
                appModel?.generateField(move.name)
                invalidate()
                return
            }
            commandWithDelay(move)
        }
    }

    fun commandWithDelay(move: Motions) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - move1 > DELAY) {
            appModel?.generateField(move.name)
            invalidate()
            move1 = currentTime
        }
        handler.sleep(DELAY.toLong())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        frame(canvas)
        if (appModel != null) {
            for (i in 0 until Count.ROW_COUNT.value) {
                for (j in 0 until Count.COLUMN_COUNT.value) {
                    drawCell(canvas, i, j)
                }
            }
        }
    }

    private fun frame(canvas: Canvas) {
        paint.color = Color.BLACK
        canvas.drawRect(
            offset.width.toFloat(),
            offset.height.toFloat(),
            width - offset.width.toFloat(),
            height - offset.height.toFloat(),
            paint
        )
    }

    private fun drawCell(canvas: Canvas, row: Int, col: Int) {
        val cellStatus = appModel?.cellStatus(row, col)
        if (EmptyEphemeral.EMPTY.value != cellStatus) {
            val color = if (EmptyEphemeral.EPHEMERAL.value == cellStatus) {
                appModel?.block?.color?.rgbValue ?: Color.TRANSPARENT
            } else {
                Block.getColor(cellStatus as Byte)
            }
            drawCell(canvas, col, row, color as Int)
        }
    }

    private fun drawCell(canvas: Canvas, x: Int, y: Int, rgbColor: Int) {
        paint.color = rgbColor
        val top: Float = (offset.height + y * size.height + BLOCK_OFFSET).toFloat()
        val left: Float = (offset.width + x * size.width + BLOCK_OFFSET).toFloat()
        val bottom: Float =
            (offset.height + (y + 1) * size.height - BLOCK_OFFSET).toFloat()
        val right: Float = (offset.width + (x + 1) * size.height - BLOCK_OFFSET).toFloat()
        val rectangle = RectF(left, top, right, bottom)
        canvas.drawRoundRect(rectangle, 4F, 4F, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val cellWidth = (w - 2 * FRAME_OFFSET_BASE) / Count.COLUMN_COUNT.value
        val cellHeight = (h - 2 * FRAME_OFFSET_BASE) / Count.ROW_COUNT.value
        val minCellSize = cellWidth.coerceAtMost(cellHeight)
        size = Dimension(minCellSize, minCellSize)
        val offsetX = (w - Count.COLUMN_COUNT.value * minCellSize) / 2
        val offsetY = (h - Count.ROW_COUNT.value * minCellSize) / 2
        offset = Dimension(offsetX, offsetY)
    }

    companion object {
        private const val DELAY = 500
        private const val BLOCK_OFFSET = 2
        private const val FRAME_OFFSET_BASE = 10
    }
}

