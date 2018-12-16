#include "Obstacle.h"

int8_t obstacle::get_velocity() const
{
	return m_velocity_;
}

void obstacle::update_x_position()
{
	m_position_x_-= m_velocity_;
}

void obstacle::reset()
{
	m_position_x_= k_screen_width - get_width();
}

void obstacle::set_velocity(const uint8_t value)
{
	m_velocity_ = value;

}
