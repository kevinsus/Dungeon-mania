package dungeonmania.goals.leaf;

import dungeonmania.Game;
import dungeonmania.goals.Goal;

public class EnemiesGoal implements Goal {
    private int enemyGoal;

    public EnemiesGoal(int enemyGoal) {
        this.enemyGoal = enemyGoal;
    }

    @Override
    public boolean achieved(Game game) {
        return game.hasEnemyGoalBeenReached(enemyGoal) && game.getNumberOfSpawners() == 0;
    }

    @Override
    public String toString(Game game) {
        return achieved(game) ? "" : ":enemies";
    }

}
