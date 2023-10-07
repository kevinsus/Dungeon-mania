package dungeonmania.entities.movement.behaviour;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class EnemyInvincibilityPotionMovementBehaviour implements EnemyMovementBehaviour {
    private Player player;

    public EnemyInvincibilityPotionMovementBehaviour(Player player) {
        this.player = player;
    }

    public void move(GameMap map, Entity entity) {
        Position entityNextPositionTowardsPlayer = map.dijkstraPathFind(entity.getPosition(), player.getPosition(),
                entity);
        Position offset;

        if (entityNextPositionTowardsPlayer.getX() != entity.getPosition().getX()) {
            offset = entityNextPositionTowardsPlayer.getX() > entity.getPosition().getX()
                    ? Position.translateBy(entity.getPosition(), Direction.RIGHT)
                    : Position.translateBy(entity.getPosition(), Direction.LEFT);
        } else if (entityNextPositionTowardsPlayer.getY() != entity.getPosition().getY()) {
            offset = entityNextPositionTowardsPlayer.getY() > entity.getPosition().getY()
                    ? Position.translateBy(entity.getPosition(), Direction.UP)
                    : Position.translateBy(entity.getPosition(), Direction.DOWN);
        } else {
            offset = entity.getPosition();
        }

        map.moveTo(entity, offset);
    }
}
