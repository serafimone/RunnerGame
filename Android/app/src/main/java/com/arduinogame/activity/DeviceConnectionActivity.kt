package com.arduinogame.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import com.arduinogame.R
import com.arduinogame.tools.BluetoothService
import com.arduinogame.tools.Constants
import kotlinx.android.synthetic.main.activity_device_connection.*

class DeviceConnectionActivity : AppCompatActivity() {
    private lateinit var listView : ListView
    private lateinit var buttonStart : Button
    private lateinit var buttonSearch : Button
    private var adapter = mutableListOf<String>()
    private var bluetoothService: BluetoothService? = null
    private var selectedMac: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_connection)
        buttonSearch = findViewById(R.id.button_start_find)
        listView = findViewById(R.id.list_device)
        buttonStart = findViewById(R.id.button_start_control)
        buttonStart.isEnabled = false
        bluetoothService = BluetoothService()
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedMac = adapter[position].split(Constants.DELIM)[0]
            buttonStart.isEnabled = true
        }
        buttonSearch.setOnClickListener {
            onSearchClick()
        }
        buttonStart.setOnClickListener {
            onStartGameClick()
        }
    }

    private fun onSearchClick() {
        if (bluetoothService!!.checkBluetooth(this)){
            adapter = bluetoothService!!.findDevices(this)
            list_device.adapter = ArrayAdapter(this@DeviceConnectionActivity, android.R.layout.simple_list_item_1, adapter)
        }
        else{
            Toast.makeText(this, Constants.MSG_BT_ADAPTER_NOT_FOUND, Toast.LENGTH_LONG).show()
        }
    }

    private fun onStartGameClick() {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("mac", selectedMac)
        startActivity(intent)
    }

}