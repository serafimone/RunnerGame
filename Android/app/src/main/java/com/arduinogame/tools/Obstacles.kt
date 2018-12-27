package com.arduinogame.tools

enum class Obstacles : EnumBase {
    Cactus {
        override fun toString(): String {
            return "Cactus"
        }

        override fun toInt(): Int {
            return 0
        }
    },
    Mushroom {
        override fun toString(): String {
            return "Mushroom"
        }

        override fun toInt(): Int {
            return 1
        }
    },
    Random {
        override fun toString(): String {
            return "Random"
        }

        override fun toInt(): Int {
            return 2
        }
    };

}