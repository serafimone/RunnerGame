#include "Game.h"
#include "GameConfig.h"

Game::Game() :
	m_current_obstacle_(nullptr),
	m_display_(U8G2_SSD1306_128X64_NONAME_F_HW_I2C(U8G2_R0)),
	m_is_game_stopped_(false),
	m_buzzer_pin_(7)
{
	m_display_.begin();
	m_display_.setFlipMode(0);
	m_display_.setFont(u8g2_font_artossans8_8r);
	m_player_ = Player(k_player_pos_x, k_objects_y, k_player_height, k_player_width, player_bits);

}

void Game::start()
{
	m_is_game_stopped_ = false;
}


void Game::draw(const bool jump)
{
	m_display_.clearBuffer();
	if (jump)
	{
		generate_jump_sound();
		m_player_.set_jump();
	}
	update_obstacle();
	draw_obstacle();
	m_player_.update_y_position();
	draw_player();
	check_collision();
	m_display_.sendBuffer();
}

void Game::draw_logo()
{
	m_display_.clearBuffer();
	m_display_.drawStr(28, 16, "DinoRunner");
	m_player_.reset();
	draw_player();
	m_current_obstacle_->reset();
	draw_obstacle();
	m_display_.sendBuffer();
}

bool Game::is_game_stopped() const
{
	return m_is_game_stopped_;
}

void Game::set_velocity(const uint8_t value)
{
	const auto obs = k_obstacles;
	for (auto i = 0; i < k_obstacles_count; i++)
	{
		obs[i].set_velocity(value);
	}
}

void Game::set_is_game_stopped(const bool value)
{
	if (value)
	{
		generate_game_over_sound();
	}
	m_is_game_stopped_ = value;
}

void Game::draw_player()
{
	m_player_.draw(&m_display_);
}

void Game::draw_obstacle() const
{
	m_current_obstacle_->draw(&m_display_);
}

void Game::update_obstacle()
{
	if (m_current_obstacle_ == nullptr)
	{
		const uint8_t random_index = 0;
		m_current_obstacle_ = &k_obstacles[random_index];
		m_current_obstacle_->reset();
	}
	const auto off_screen = 0 - m_current_obstacle_->get_width();
	if (m_current_obstacle_->get_position_x() < off_screen)
	{
		m_current_obstacle_->reset();
		m_current_obstacle_ = nullptr;
		return;
	}
	m_current_obstacle_->update_x_position();
}

void Game::check_collision()
{
	if (m_current_obstacle_ == nullptr)
	{
		return;
	}
	const auto left_player_x = m_player_.get_position_x();
	const int8_t right_player_x = m_player_.get_position_x() + m_player_.get_width() - m_player_.get_width() * 0.2;
	const auto player_position_y = m_player_.get_position_y();

	const auto left_obstacle_x = m_current_obstacle_->get_position_x();
	const int8_t right_obstacle_x = m_current_obstacle_->get_position_x() + m_current_obstacle_->get_width() - m_player_.get_width() * 0.2;

	const auto player_before_obs = right_player_x < left_obstacle_x;
	const auto player_after_obs = left_player_x > right_obstacle_x;
	const auto player_above_obs = player_position_y > m_current_obstacle_->get_height();

	const auto player_collides_x_obstacle = !player_before_obs && !player_after_obs;

	set_is_game_stopped(player_collides_x_obstacle && !player_above_obs);
}

void Game::generate_jump_sound() const
{
	analogWrite(m_buzzer_pin_, 10);
	delay(10);
	analogWrite(m_buzzer_pin_, 255);
	delay(5);
	analogWrite(m_buzzer_pin_, 10);
	delay(10);
	analogWrite(m_buzzer_pin_, 255);
}

void Game::generate_game_over_sound() const
{
	analogWrite(m_buzzer_pin_, 10);
	delay(500);
	analogWrite(m_buzzer_pin_, 255);
}
