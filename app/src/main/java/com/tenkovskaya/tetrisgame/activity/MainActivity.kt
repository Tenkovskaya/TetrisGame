package com.tenkovskaya.tetrisgame.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tenkovskaya.tetrisgame.Preferences
import com.tenkovskaya.tetrisgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var uyhiwjoekfijwefwefoijwjef: ActivityMainBinding
    private lateinit var woeifewe: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uyhiwjoekfijwefwefoijwjef = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        woeifewe = Preferences(this)

        uyhiwjoekfijwefwefoijwjef.btnNewGame.setOnClickListener(this::iuwefwef)
    }

    private fun iuwefwef(view: View) {
        val uiwokpfwjief = Intent(this, GameActivity::class.java)
        startActivity(uiwokpfwjief)
    }
}