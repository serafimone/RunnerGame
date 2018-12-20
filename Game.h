#pragma once
#include "Player.h"
#include "Obstacle.h"
#include "U8g2lib.h"

static const int8_t test_velocity = 1;

class Game
{
public:
	static Game& instance()
	{
		static Game g;
		return g;
	}
	Game(Game const&) = delete;
	Game& operator= (Game const&) = delete;
	Game();
	void start();
	void draw(bool jump);
	void draw_logo();
	bool is_game_stopped() const;
	static void set_velocity(uint8_t value);
private:
	Player m_player_;
	Obstacle* m_current_obstacle_;
	U8G2_SSD1306_128X64_NONAME_F_HW_I2C m_display_;
	bool m_is_game_stopped_;
	uint8_t m_buzzer_pin_;

	void set_is_game_stopped(bool value);
	void draw_player();
	void draw_obstacle() const;
	void update_obstacle();
	void check_collision();
	void generate_jump_sound() const;
	void generate_game_over_sound() const;
};
