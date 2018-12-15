 #include "Player.h"

bool Player::isOnGround()
{
	return m_onGround;
}

void Player::setJump()
{
	if (m_onGround) {
		m_VelocityY = k_Velocity;
	}
}

void Player::updateYPosition()
{
	if (m_onGround) {
		if (m_VelocityY <= 0) {
			m_VelocityY = 0;
			return;
		}
		else {
			m_PositionY += m_VelocityY;
			if (m_PositionY < 0) {
				m_PositionY= 0;
			}
			m_VelocityY -= k_Gravity;
		}
	}
}
