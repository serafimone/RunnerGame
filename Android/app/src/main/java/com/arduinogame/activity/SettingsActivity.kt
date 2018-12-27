package com.arduinogame.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.arduinogame.R
import com.arduinogame.tools.Constants
import com.arduinogame.tools.Obstacles
import com.arduinogame.tools.SettingsParams
import com.arduinogame.tools.Speed
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setAdapters()
        setListeners()
    }

    private fun setAdapters() {
        val opponentsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Constants.OPPONENTS_ARRAY)
        val speedAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Constants.SPEED_ARRAY)
        opponent_spinner.adapter = opponentsAdapter
        opponent_spinner.setSelection(opponentsAdapter.getPosition(SettingsParams.Instance.getValueByKey("obstacle")!!.toString()))
        speed_spinner.adapter = speedAdapter
        speed_spinner.setSelection(speedAdapter.getPosition(SettingsParams.Instance.getValueByKey("speed")!!.toString()))
    }

    private fun setListeners(){
        opponent_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                SettingsParams.Instance.add("obstacle", Obstacles.Cactus)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent!!.getItemAtPosition(position).toString()
                SettingsParams.Instance.add("obstacle", Obstacles.valueOf(item))
            }

        }

        speed_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                SettingsParams.Instance.add("speed", Speed.Low)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent!!.getItemAtPosition(position).toString()
                SettingsParams.Instance.add("speed", Speed.valueOf(item))
            }
        }
    }

    fun onSaveClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
