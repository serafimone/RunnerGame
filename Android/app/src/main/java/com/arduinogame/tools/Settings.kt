package com.arduinogame.tools

class SettingsParams {
    private var values : HashMap<String, EnumBase> = HashMap()

    init {
        values["obstacle"] = Obstacles.Mushroom
        values["speed"] = Speed.Low
    }

    private object Holder {val INSTANCE = SettingsParams()}

    companion object {
        val Instance : SettingsParams by lazy { Holder.INSTANCE }
    }

    fun add(key : String, value: EnumBase) {
        values[key] = value
    }

    fun getValueByKey(key: String) : EnumBase? {
        return values[key]
    }
}