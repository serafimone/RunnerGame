package com.arduinogame.tools

import java.util.*

interface Constants {
    companion object {
        const val DELIM = "/"
        const val MSG_CONNECTION_FAILED = "Сonnection not established"
        const val MSG_BT_ADAPTER_NOT_FOUND = "Bluetooth adapter not found"
        const val MSG_BT_NOT_AVAILABLE = "Bluetooth is not available"
        const val MSG_BT_NOT_ENABLED = "Bluetooth is not enabled"
        const val MSG_DEVICES_NOT_FOUND = "Devices not found"
        const val MSG_GAME_OVER = "Game over!"
        const val MSG_GAME_STARTED = "GO!"

        val SPEED_ARRAY = Speed.values()
            .map { element -> element.toString()}
            .toTypedArray()
        val OPPONENTS_ARRAY = Obstacles.values()
            .map { element -> element.toString()}
            .toTypedArray()
        val CONNECTION_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")!!
        val JUMP_SENT_MSG = "!".toByteArray()
    }
}