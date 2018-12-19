package com.arduinogame.activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.Toast
import com.arduinogame.Constants
import com.arduinogame.R
import com.arduinogame.activity.DeviceConnectionActivity.Companion.mBluetoothSocket as socket


class GameActivity : AppCompatActivity() {

    private var mBtnJump: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        onBtnJumpClick()
        val game = GameTask()
        game.execute()
   }

    fun onBtnJumpClick(){
        mBtnJump = findViewById(R.id.btnJump)
        mBtnJump!!.setOnClickListener {
            socket!!.outputStream.write(Constants.JUMP_SENT_MSG)
        }
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
                    messageSize = DeviceConnectionActivity.mBluetoothSocket!!.inputStream.read()
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
            socket!!.close()
            val intent = Intent(this@GameActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }


}
