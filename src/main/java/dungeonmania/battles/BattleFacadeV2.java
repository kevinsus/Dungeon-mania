package dungeonmania.battles;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;

public class BattleFacadeV2 extends BattleFacade {
    private int playerKillCount = 0;

    @Override
    public void battle(Game game, Player player, Enemy enemy) {
        super.battle(game, player, enemy);
        if (player.getBattleStatistics().getHealth() > 0)
            playerKillCount++;
    }

    public boolean hasEnemyGoalBeenReached(int enemyGoal) {
        return playerKillCount >= enemyGoal;
    }
}
