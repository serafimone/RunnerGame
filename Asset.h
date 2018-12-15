class Asset
{
public:
	Asset() {};
	Asset(uint8_t x, uint8_t y, uint8_t h, uint8_t w, uint8_t *bitMap);
	uint8_t getPositionX();
	uint8_t getPositionY();
	uint8_t getHeight();
	uint8_t getWidth();
	uint8_t* getBitMap();
	template <typename TRenderer> void Draw(TRenderer* renderer) {
		int8_t positionYFromGround = k_ScreenHeight - getHeight()- getPositionY();
		renderer->drawXBM(
			getPositionX(),
			positionYFromGround,
			getWidth(),
			getHeight(),
			getBitmap());
	}

protected: 
	uint8_t m_PositionX;
	uint8_t m_PositionY;
	uint8_t m_Height;
	uint8_t m_Width;
	uint8_t *m_bitMap;
};

