#include "Asset.h"

Asset::Asset(uint8_t x, uint8_t y, uint8_t h, uint8_t w, uint8_t *bitMap)
{
	m_PositionX = x;
	m_PositionY = y;
	m_Height = h;
	m_Width = y;
	m_bitMap = bitMap;
}

uint8_t Asset::getPositionX()
{
	return uint8_t();
}

uint8_t Asset::getPositionY()
{
	return uint8_t();
}

uint8_t Asset::getHeight()
{
	return uint8_t();
}

uint8_t Asset::getWidth()
{
	return uint8_t();
}

uint8_t* Asset::getBitMap()
{
	return m_bitMap;
}
