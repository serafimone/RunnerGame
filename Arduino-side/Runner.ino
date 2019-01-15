#include <U8x8lib.h>
#include "Game.h"
#include "U8g2lib.h"
#ifdef U8X8_HAVE_HW_SPI
#endif
#ifdef U8X8_HAVE_HW_I2C
#include <Wire.h>
#endif

Game* instance;


void setup() {
	
	Serial.begin(9600);
}

void loop() {
	if (Game::instance().is_game_stopped())
	{
		Game::instance().draw_logo();
		if (Serial.available())
		{
			const uint8_t speed = Serial.read();
			const uint8_t obstacle = Serial.read();
			Game::instance().start(obstacle, speed);
		}
		return;
	}
	auto val = false;
	if (Serial.available())
	{
		val = Serial.read() == '!';
	}
	Game::instance().draw(val);
}
