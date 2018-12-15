#include "Game.h"
#include "Assets.h"

void Game::drawPlayer()
{
	m_Player.Draw(m_Display);
}

void Game::drawObstacle()
{
	m_Obstacles[0].Draw(m_Display);
}

void Game::updateObstacle()
{
	auto off_screen = 0 - m_CurrentObstacle->getWidth();
	if (m_CurrentObstacle->getPositionX() <= off_screen) {
		m_CurrentObstacle->Reset();
		m_CurrentObstacle = nullptr;
		return;
	}
	m_CurrentObstacle->updateXPosition();
}
void Game::checkCollision()
{

}
}
