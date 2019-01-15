#pragma once
#include "Asset.h"

class Player : public Asset
{
public:
	Player(): m_velocity_y_(0)
	{
	}
	Player(
		const uint8_t x,
		const uint8_t y,
		const uint8_t h,
		const uint8_t w,
		uint8_t *bit_map) : Asset(x, y, h, w, bit_map), m_velocity_y_(0) {}
	bool is_on_ground() const;
	void set_jump();
	void update_y_position();
	void reset();
private:
	int8_t m_velocity_y_;
	static const uint8_t k_velocity = 9;
	static const uint8_t k_gravity = 1;
};

