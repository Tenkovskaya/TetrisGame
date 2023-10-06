package com.tenkovskaya.tetrisgame.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tenkovskaya.tetrisgame.Preferences
import com.tenkovskaya.tetrisgame.databinding.ActivityGameBinding
import com.tenkovskaya.tetrisgame.models.AppModel
import com.tenkovskaya.tetrisgame.models.Motions
import com.tenkovskaya.tetrisgame.view.TView

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var tView: TView
    private val appModel: AppModel = AppModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        setupListeners()
        configureAppModel()
    }

    private fun initializeViews() {
        tView = binding.viewTetris
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListeners() {
        tView.setOnTouchListener { view, event -> handleTouchEvent(view, event) }
        binding.btnRestart.setOnClickListener { appModel.restartGame() }
    }

    private fun configureAppModel() {
        appModel.preferences(Preferences(this))
        tView.activity(this)
        tView.model(appModel)
    }

    private fun handleTouchEvent(view: View, event: MotionEvent): Boolean {
        when {
            appModel.gameOver() || appModel.start() -> {
                appModel.game()
                tView.commandWithDelay(Motions.DOWN)
            }
            appModel.active() -> {
                val direction = determineGestureDirection(view, event)
                executeMotion(direction)
            }
        }
        return true
    }

    private fun determineGestureDirection(view: View, event: MotionEvent): Motions {
        val x = event.x / view.width
        val y = event.y / view.height
        return if (y > x) {
            if (x > 1 - y) Motions.DOWN else Motions.LEFT
        } else {
            if (x > 1 - y) Motions.RIGHT else Motions.ROTATE
        }
    }

    private fun executeMotion(motion: Motions) {
        if (appModel.active()) {
            tView.command(motion)
        }
    }
}
