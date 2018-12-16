#include "Player.h"

bool player::is_on_ground() const
{
	return m_position_y_ == 0;
}

void player::set_jump()
{
	if (is_on_ground())
	{
		m_velocity_y_ = k_velocity;
	}
}

void player::update_y_position()
{
	if (is_on_ground() && m_velocity_y_ <= 0)
	{
		m_velocity_y_ = 0;
	}
	else if (!is_on_ground() || m_velocity_y_ > 0)
	{
		m_position_y_ += m_velocity_y_;
		if (m_position_y_ < 0)
		{
			m_position_y_ = 0;
		}
		m_velocity_y_ -= k_gravity;
	}
}

