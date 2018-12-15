#include "Asset.h"
class Obstacle : public Asset
{
public:
	Obstacle() {};
	Obstacle(uint8_t x, uint8_t y, uint8_t h, uint8_t w, uint8_t *bitMap, int8_t velocity) : Asset(x, y, h, w, bitMap) {
		m_Velocity = velocity;
	};
	int8_t getVelocity();
	void updateXPosition();
	void reset();
private:
	int8_t m_Velocity;
};

