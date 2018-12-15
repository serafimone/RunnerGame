#include "Player.h"
#include "Obstacle.h"

static const int8_t TEST_VELOCITY = 1;
class Game
{
public:
	Game(U8G2_SSD1306_128X64_NONAME_F_HW_I2C* display) {
		m_Player = Player(k_PlayerPosX, k_ScreenWidth - 1, k_PlayerHeight, k_PlayerWidth, player_bits);
		m_Obstacles[0] = Obstacle(
			k_ObstacleStartPosX, 
			k_ScreenWidth - 1, 
			k_ObstacleHeight, 
			k_ObstacleWidth, 
			mushroom_bits,
			TEST_VELOCITY);
		m_Display = display;
		m_CurrentObstacle = nullptr;
		m_isGameStopped = false;
	};
	void draw(bool jump);
	bool isGameStopped();
private: 
	Player m_Player;
	Obstacle* m_Obstacles;
	Obstacle* m_CurrentObstacle;
	U8G2_SSD1306_128X64_NONAME_F_HW_I2C* m_Display;
	bool m_isGameStopped;

	void drawPlayer();
	void drawObstacle();
	void updateObstacle();
	void checkCollision();
};

