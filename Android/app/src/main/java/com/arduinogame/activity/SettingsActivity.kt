package com.arduinogame.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.arduinogame.Constants
import com.arduinogame.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setAdapters()
        setListeners()
    }

    fun setAdapters() {
        val opponentsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Constants.OPPONENTS_ARRAY)
        val speedAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Constants.SPEED_ARRAY)
        opponent_spinner.adapter = opponentsAdapter
        speed_spinner.adapter = speedAdapter
    }

    fun setListeners(){
        opponent_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                opponent = 0
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                opponent = position
            }

        }

        speed_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                speed = 0
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                speed = position
            }
        }
    }

    fun onSaveClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        var opponent = 0
        var speed = 0
    }
}
