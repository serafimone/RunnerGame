#pragma once
#include "Asset.h"

class obstacle : public asset
{
public:
	obstacle()
	{
	};

	obstacle(const uint8_t x, const uint8_t y, const uint8_t h, const uint8_t w, uint8_t* bit_map,
	         const int8_t velocity) : asset(x, y, h, w, bit_map)
	{
		m_velocity_ = velocity;
	};
	int8_t get_velocity() const;
	void update_x_position();
	void reset();
	void set_velocity(uint8_t value);
private:
	int8_t m_velocity_;
};
