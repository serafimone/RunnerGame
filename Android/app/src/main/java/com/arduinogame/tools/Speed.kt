package com.arduinogame.tools

enum class Speed : EnumBase {
    Low {
        override fun toString(): String {
            return "Low";
        }

        override fun toInt(): Int {
            return 2
        }
    },
    Medium {
        override fun toString(): String {
            return "Medium"
        }

        override fun toInt(): Int {
            return 4
        }
    };
}