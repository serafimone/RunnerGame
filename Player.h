#include "Asset.h"
class Player : public Asset
{
public:
	Player(){ }
	Player(
		uint8_t x, 
		uint8_t y, 
		uint8_t h, 
		uint8_t w, 
		uint8_t *bitMap) : Asset::Asset(x, y, h, w, bitMap) {
			
	};
	bool isOnGround();
	void setJump();
	void updateYPosition();
private:
	bool m_onGround;
	int8_t m_VelocityY;
	static const uint8_t k_Velocity = 9;
	static const uint8_t k_Gravity = 1;
};

