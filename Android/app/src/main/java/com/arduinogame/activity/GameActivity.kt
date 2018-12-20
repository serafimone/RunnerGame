package com.arduinogame.activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.Toast
import com.arduinogame.R
import com.arduinogame.tools.BluetoothService
import com.arduinogame.tools.Constants
import com.arduinogame.tools.Settings.Companion
import java.io.IOException

class GameActivity : AppCompatActivity() {

    private var mBtnJump: ImageButton? = null
    private var mBluetoothService: BluetoothService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val intent = getIntent()
        mBluetoothService = BluetoothService()
        mBtnJump = findViewById(R.id.btnJump)
        mBtnJump!!.isEnabled = false
        setOnBtnJumpClick()
        val deviceMAC = intent.getStringExtra("mac")
        val game = GameTask()
        if (tryConnect(deviceMAC) && startGame()){
            mBtnJump!!.isEnabled = true
            game.execute()
        }
    }

    fun tryConnect(deviceMAC: String):Boolean{
        if(mBluetoothService!!.checkBluetooth(this))
            return mBluetoothService!!.getConnection(deviceMAC, this)
        return false
    }

    fun setOnBtnJumpClick(){
        mBtnJump!!.setOnClickListener {
            mBluetoothService!!.sendMessage(Constants.JUMP_SENT_MSG)
        }
    }

    fun startGame(): Boolean {
        try {
            val message = (Companion.speed.toString()+Companion.opponent.toString()).toByteArray()
            mBluetoothService!!.sendMessage(message)  //Settings sent. Game started!
            Toast.makeText(this@GameActivity, Constants.MSG_GAME_STARTED, Toast.LENGTH_LONG).show()
            return true
        } catch (e: IOException) {

        }
        return false
    }

    internal inner class GameTask : AsyncTask<Void, Void, Void>() {

        private var mTime: Chronometer? = null

        override fun onPreExecute() {
            super.onPreExecute()
            mTime = findViewById(R.id.chronometer)
            mTime!!.start()
        }

        override fun doInBackground(vararg params: Void): Void? {
            var messageSize = 0
            while(messageSize == 0) {
                try {
                    messageSize = mBluetoothService!!.readMessage()
                } catch (e: Exception) {
                }
            }
            return null
        }

        override fun onPostExecute(result: Void) {
            super.onPostExecute(result)
            gameOver()
        }

        private fun gameOver(){
            mTime!!.stop()
            mBtnJump!!.isEnabled = false
            Toast.makeText(this@GameActivity, Constants.MSG_GAME_OVER, Toast.LENGTH_LONG).show()
            mBluetoothService!!.stopService()
            val intent = Intent(this@GameActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

}


