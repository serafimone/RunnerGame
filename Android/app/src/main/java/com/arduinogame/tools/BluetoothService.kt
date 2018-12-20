package com.arduinogame.tools

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.widget.Toast
import java.lang.Integer.TYPE

class BluetoothService {
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mPairedDevices: Set<BluetoothDevice>? = null
    private var mBluetoothSocket: BluetoothSocket? = null

    fun checkBluetooth(context: Context):Boolean {
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            if (mBluetoothAdapter == null) {
                Toast.makeText(context, Constants.MSG_BT_NOT_AVAILABLE, Toast.LENGTH_LONG).show()
            }

            if (!mBluetoothAdapter!!.isEnabled) {
                Toast.makeText(context, Constants.MSG_BT_NOT_ENABLED, Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun sendMessage(msg : ByteArray){
        mBluetoothSocket!!.outputStream.write(msg)
    }

    fun readMessage(): Int{
        return mBluetoothSocket!!.inputStream.read()
    }

    fun stopService(){
        mBluetoothSocket!!.close()
    }

    fun getConnection(deviceMAC: String, context: Context):Boolean{
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
            Toast.makeText(context, Constants.MSG_CONNECTION_FAILED, Toast.LENGTH_LONG).show()
            return false
        }
        return mBluetoothSocket!!.isConnected()
    }

    fun findDevices(context: Context): MutableList<String> {
        var mAdapter = mutableListOf<String>()
        mPairedDevices = mBluetoothAdapter!!.bondedDevices
        if (mPairedDevices!!.isNotEmpty()) {
            for (device: BluetoothDevice in mPairedDevices!!)
                mAdapter.add(device.address + Constants.DELIM + device.name)
        } else {
            Toast.makeText(context, Constants.MSG_DEVICES_NOT_FOUND, Toast.LENGTH_LONG).show()
        }
        return mAdapter
    }
}