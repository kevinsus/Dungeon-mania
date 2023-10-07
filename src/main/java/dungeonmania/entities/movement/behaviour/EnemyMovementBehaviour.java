package dungeonmania.entities.movement.behaviour;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public interface EnemyMovementBehaviour {
    public void move(GameMap map, Entity entity);
}
