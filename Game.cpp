#include "Game.h"
#include "GameConfig.h"

game::game() :
	m_current_obstacle_(nullptr),
	m_display_(U8G2_SSD1306_128X64_NONAME_F_HW_I2C(U8G2_R0)),
	m_is_game_stopped_(false)
{
	m_display_.begin();
	m_display_.setFlipMode(0);
	m_player_ = player(k_player_pos_x, 0, k_player_height, k_player_width, player_bits);
}


void game::draw(const bool jump)
{
	m_display_.clearBuffer();
	if (jump)
	{
		m_player_.set_jump();
	}
	update_obstacle();
	draw_obstacle();
	m_player_.update_y_position();
	draw_player();
	check_collision();
	m_display_.sendBuffer();
}

bool game::is_game_stopped() const
{
	return m_is_game_stopped_;
}

void game::set_velocity(const uint8_t value)
{
	const auto obs = k_obstacles;
	for (auto i = 0; i < k_obstacles_count; i++)
	{
		obs[i].set_velocity(value);
	}
}

void game::draw_player()
{
	m_player_.draw(&m_display_);
}

void game::draw_obstacle() const
{
	m_current_obstacle_->draw(&m_display_);
}

void game::update_obstacle()
{
	if (m_current_obstacle_ == nullptr)
	{
		const uint8_t random_index = 0;
		m_current_obstacle_ = &k_obstacles[random_index];
		m_current_obstacle_->reset();
	}
	const auto off_screen = 0 - m_current_obstacle_->get_width();
	if (m_current_obstacle_->get_position_x() <= off_screen)
	{
		m_current_obstacle_->reset();
		m_current_obstacle_ = nullptr;
		return;
	}
	m_current_obstacle_->update_x_position();
}

void game::check_collision()
{
	if (m_current_obstacle_ == nullptr)
	{
		return;
	}
	const auto left_player_x = m_player_.get_position_x();
	const int8_t right_player_x = m_player_.get_position_x() + m_player_.get_width() - 2;
	const auto player_position_y = m_player_.get_position_x();

	const auto left_obstacle_x = m_current_obstacle_->get_position_x();
	const int8_t right_obstacle_x = m_current_obstacle_->get_position_x() + m_current_obstacle_->get_width();

	const auto player_before_obs = right_player_x < left_obstacle_x;
	const auto player_after_obs = left_player_x > right_obstacle_x;
	const auto player_above_obs = player_position_y > m_current_obstacle_->get_height();

	const auto player_collides_x_obstacle = !player_before_obs && !player_after_obs;

	m_is_game_stopped_ = player_collides_x_obstacle && !player_above_obs;
}
	