#pragma once
#include <stdint.h>
#include "ScreenConfig.h"

class Asset
{
public:
	Asset(const uint8_t x, const uint8_t y, const uint8_t h, const uint8_t w, uint8_t* bit_map) :
		m_position_x_(x),
		m_position_y_(y),
		m_height_(h),
		m_width_(w),
		m_bit_map_(bit_map)
	{
	};

	Asset(): m_position_x_(0), m_position_y_(0), m_height_(0), m_width_(0), m_bit_map_(nullptr)
	{
	} ;
	int8_t get_position_x() const;
	int8_t get_position_y() const;
	uint8_t get_height() const;
	uint8_t get_width() const;
	uint8_t* get_bit_map() const;

	template <typename TRenderer>
	void draw(TRenderer* renderer)
	{
		int8_t positionYFromGround = k_screen_height - get_height() - get_position_y();
		renderer->drawXBM(
			get_position_x(),
			positionYFromGround,
			get_width(),
			get_height(),
			get_bit_map());
	}

protected:
	int8_t m_position_x_;
	int8_t m_position_y_;
	uint8_t m_height_;
	uint8_t m_width_;
	uint8_t* m_bit_map_;
};
