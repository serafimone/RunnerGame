#include <U8x8lib.h>
#include <U8g2lib.h>

U8G2_SSD1306_128X64_NONAME_F_HW_I2C display(U8G2_R0);
Game game;

void setup()
{
	display.begin();
	display.setFlipMode(0);
	game = Game(&display);
}

void loop()
{
	game.draw();
}
