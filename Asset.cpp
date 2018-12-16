#include "Asset.h"

int8_t asset::get_position_x() const
{
	return m_position_x_;
}

int8_t asset::get_position_y() const
{
	return m_position_y_;
}

uint8_t asset::get_height() const
{
	return m_height_;
}

uint8_t asset::get_width() const
{
	return m_width_;
}

uint8_t* asset::get_bit_map() const
{
	return m_bit_map_;
}