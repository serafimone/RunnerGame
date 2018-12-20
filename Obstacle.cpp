#include "Obstacle.h"

int8_t Obstacle::get_velocity() const
{
	return m_velocity_;
}

void Obstacle::update_x_position()
{
	m_position_x_-= m_velocity_;
}

void Obstacle::reset()
{
	m_position_x_= k_screen_width - get_width() - 2;
}

void Obstacle::set_velocity(const uint8_t value)
{
	m_velocity_ = value;

}
