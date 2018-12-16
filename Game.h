#pragma once
#include "Player.h"
#include "Obstacle.h"
#include "U8g2lib.h"

static const int8_t test_velocity = 1;

class game
{
public:
	static game& instance()
	{
		static game g;
		return g;
	}
	game(game const&) = delete;
	game& operator= (game const&) = delete;
	game();
	void draw(bool jump);
	bool is_game_stopped() const;
	static void set_velocity(uint8_t value);
private:
	player m_player_;
	obstacle* m_current_obstacle_;
	U8G2_SSD1306_128X64_NONAME_F_HW_I2C m_display_;
	bool m_is_game_stopped_;

	void draw_player();
	void draw_obstacle() const;
	void update_obstacle();
	void check_collision();

};
