package com.arduinogame.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.arduinogame.R
import com.arduinogame.tools.BluetoothService
import com.arduinogame.tools.Constants
import kotlinx.android.synthetic.main.activity_device_connection.*

class DeviceConnectionActivity : AppCompatActivity() {
    private var mAdapter = mutableListOf<String>()
    private var mBluetoothService: BluetoothService? = null
    private var selectedItemMac: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_connection)
        button_start_control.isEnabled = false
        mBluetoothService = BluetoothService()
        setListDeviceListener()
    }

    fun setListDeviceListener(){
        list_device.setOnItemClickListener { parent, view, position, id ->
            selectedItemMac = mAdapter[position].split(Constants.DELIM)[0]
        }
    }

    fun onSearchClick(view: View) {
        if (mBluetoothService!!.checkBluetooth(this)){
            mAdapter = mBluetoothService!!.findDevices(this)
            list_device.adapter = ArrayAdapter(this@DeviceConnectionActivity, android.R.layout.simple_list_item_1, mAdapter)
        }
        else{
            Toast.makeText(this, Constants.MSG_BT_ADAPTER_NOT_FOUND, Toast.LENGTH_LONG).show()
        }
    }

    fun onStartGameClick(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("mac", selectedItemMac)
        startActivity(intent)
    }

}