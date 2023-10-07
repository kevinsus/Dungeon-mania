package dungeonmania.entities.movement.behaviour.mercenary;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.movement.behaviour.EnemyMovementBehaviour;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class EnemyAlliedMercenaryMovementBehaviour implements EnemyMovementBehaviour {
    private Player player;

    public EnemyAlliedMercenaryMovementBehaviour(Player player) {
        this.player = player;
    }

    public void move(GameMap map, Entity entity) {
        Position positionBetweenEntityAndPlayer = Position.calculatePositionBetween(entity.getPosition(),
                player.getPreviousDistinctPosition());

        Position nextPos;
        if (Math.abs(positionBetweenEntityAndPlayer.getX()) <= 1
                && Math.abs(positionBetweenEntityAndPlayer.getY()) <= 1)
            nextPos = player.getPreviousDistinctPosition();
        else
            nextPos = map.dijkstraPathFind(entity.getPosition(), player.getPosition(), entity);

        map.moveTo(entity, nextPos);
    }
}
