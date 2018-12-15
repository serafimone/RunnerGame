#include "Obstacle.h"
#include "ScreenConfig.h"

int8_t Obstacle::getVelocity()
{
	return m_Velocity;
}

void Obstacle::updateXPosition()
{
	m_PositionX -= m_Velocity;
}

void Obstacle::reset()
{
	m_PositionX = k_ScreenWidth + getWidth();
}
