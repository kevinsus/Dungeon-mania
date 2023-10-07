package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.enemies.movementutil.ZombieToastMovementUtil;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;

    private ZombieToastMovementUtil zombieToastMovement;

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);
        zombieToastMovement = new ZombieToastMovementUtil();
    }

    @Override
    public void move(Game game) {
        GameMap map = game.getMap();
        zombieToastMovement.move(map, this, map.getPlayer());
    }

}
