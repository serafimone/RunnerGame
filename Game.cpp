#include "Game.h"
#include "Assets.h"

void Game::draw(bool jump)
{
	m_Display->clearBuffer();
	if (jump) {
		m_Player.setJump();
	}
	updateObstacle();
	m_Player.updateYPosition();
	checkCollision();
	m_Display->sendBuffer();
}

bool Game::isGameStopped()
{
	return m_isGameStopped;
}

void Game::drawPlayer()
{
	m_Player.Draw(m_Display);
}

void Game::drawObstacle()
{
	m_CurrentObstacle->Draw(m_Display);
}

void Game::updateObstacle()
{
	if (m_CurrentObstacle == nullptr) {
		uint8_t random_index = random(0, 2);
		m_CurrentObstacle = &m_Obstacles[random_index];
		m_CurrentObstacle->reset();
	}
	auto offScreen = 0 - m_CurrentObstacle->getWidth();
	if (m_CurrentObstacle->getPositionX() <= offScreen) {
		m_CurrentObstacle->reset();
		m_CurrentObstacle = nullptr;
		return;
	}
	m_CurrentObstacle->updateXPosition();
}
void Game::checkCollision()
{
	if (m_CurrentObstacle == nullptr) {
		return;
	}
	int8_t leftPlayerX = m_Player.getPositionX();
	int8_t rightPlayerX = m_Player.getPositionX() + m_Player.getWidth();
	int8_t playerPositionY= m_Player.getPositionY();

	int8_t leftObstacleX = m_CurrentObstacle->getPositionX();
	int8_t rightObstacleX = m_CurrentObstacle->getPositionX()+ m_CurrentObstacle->getWidth;

	int8_t top_obstacle = m_CurrentObstacle->getHeight();

	bool playerBeforeObs = rightPlayerX < leftObstacleX;
	bool playerAfterObs = leftPlayerX > rightObstacleX;
	bool playerAboveObs = playerPositionY> top_obstacle;

	bool player_collides_x_obstacle = !playerBeforeObs && !playerAfterObs;

	m_isGameStopped = player_collides_x_obstacle && !playerAboveObs;
}
