package com.arduinogame.activity

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.arduinogame.Constants
import com.arduinogame.R
import kotlinx.android.synthetic.main.activity_device_connection.*
import java.io.IOException
import java.lang.Integer.TYPE

class DeviceConnectionActivity : AppCompatActivity() {
    private var mAdapter = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_connection)
        button_start_control.isEnabled = false
        setListDeviceListener()
    }

    fun setListDeviceListener(){
        list_device.setOnItemClickListener { parent, view, position, id ->
            val itemMAC: String = mAdapter[position].split(Constants.DELIM)[0]
            if (BluetoothService().getConnection(itemMAC)) button_start_control.isEnabled = true
        }
    }

    fun onSearchClick(view: View) {
        if (BluetoothService().checkBluetooth())
            BluetoothService().findDevices()
        else{
            Toast.makeText(this, Constants.MSG_BT_ADAPTER_NOT_FOUND, Toast.LENGTH_LONG).show()
        }
    }

    fun onStartGameClick(view: View) {
        try {
            val message = (SettingsActivity.speed.toString()+SettingsActivity.opponent.toString()).toByteArray()
            mBluetoothSocket!!.outputStream.write(message)  //Settings sent. Game started!
        } catch (e: IOException) {

        }
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }


    inner class BluetoothService{

        private var mBluetoothAdapter: BluetoothAdapter? = null
        private var mPairedDevices: Set<BluetoothDevice>? = null

        fun checkBluetooth():Boolean {
            try {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                if (mBluetoothAdapter == null) {
                    Toast.makeText(this@DeviceConnectionActivity, Constants.MSG_BT_NOT_AVAILABLE, Toast.LENGTH_LONG).show()
                }

                if (!mBluetoothAdapter!!.isEnabled) {
                    Toast.makeText(this@DeviceConnectionActivity, Constants.MSG_BT_NOT_ENABLED, Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                return false
            }
            return true
        }

        fun getConnection(deviceMAC: String):Boolean{
            try {
                val device: BluetoothDevice = mBluetoothAdapter!!.getRemoteDevice(deviceMAC)
                mBluetoothSocket = device.createRfcommSocketToServiceRecord(Constants.CONNECTION_UUID)
                mBluetoothAdapter!!.cancelDiscovery()
                try{
                    mBluetoothSocket!!.connect()
                } catch(e: Exception) {
                    mBluetoothSocket = device.javaClass.getMethod("createRfcommSocket", *arrayOf<Class<Int>>(TYPE)).invoke(device, 1) as BluetoothSocket
                    mBluetoothSocket!!.connect()
                }
            } catch (e: Exception) {
                Toast.makeText(this@DeviceConnectionActivity, Constants.MSG_CONNECTION_FAILED, Toast.LENGTH_LONG).show()
                return false
            }
            return mBluetoothSocket!!.isConnected()
        }

        fun findDevices() {
            mPairedDevices = mBluetoothAdapter!!.bondedDevices
            if (mPairedDevices!!.isNotEmpty()) {
                for (device: BluetoothDevice in mPairedDevices!!)
                    mAdapter.add(device.address + Constants.DELIM + device.name)
                val adapter = ArrayAdapter(this@DeviceConnectionActivity, android.R.layout.simple_list_item_1, mAdapter)
                list_device.adapter = adapter
            } else {
                Toast.makeText(this@DeviceConnectionActivity, Constants.MSG_DEVICES_NOT_FOUND, Toast.LENGTH_LONG).show()
            }

        }
    }

    companion object {
        var mBluetoothSocket: BluetoothSocket? = null
    }
}