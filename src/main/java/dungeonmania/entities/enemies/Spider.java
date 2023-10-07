package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movement.behaviour.spider.EnemySpiderStandardMovementBehaviour;
import dungeonmania.util.Position;

public class Spider extends Enemy {
    private EnemySpiderStandardMovementBehaviour spiderMovement;

    public static final int DEFAULT_SPAWN_RATE = 0;
    public static final double DEFAULT_ATTACK = 5;
    public static final double DEFAULT_HEALTH = 10;

    public Spider(Position position, double health, double attack) {
        super(position.asLayer(Entity.DOOR_LAYER + 1), health, attack);
        /**
         * Establish spider movement trajectory Spider moves as follows:
         *  8 1 2       10/12  1/9  2/8
         *  7 S 3       11     S    3/7
         *  6 5 4       B      5    4/6
         */

        spiderMovement = new EnemySpiderStandardMovementBehaviour(position.getAdjacentPositions(), 1, true);
    };

    @Override
    public void move(Game game) {
        spiderMovement.move(game.getMap(), this);
    }
}
