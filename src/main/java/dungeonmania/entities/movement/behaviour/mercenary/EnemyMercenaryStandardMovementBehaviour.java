package dungeonmania.entities.movement.behaviour.mercenary;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.SwampTile;
import dungeonmania.entities.movement.behaviour.EnemyMovementBehaviour;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class EnemyMercenaryStandardMovementBehaviour implements EnemyMovementBehaviour {
    private Player player;

    public EnemyMercenaryStandardMovementBehaviour(Player player) {
        this.player = player;
    }

    public void move(GameMap map, Entity entity) {
        Position nextPos = map.dijkstraPathFind(entity.getPosition(), player.getPosition(), entity);
        // If the entity is on a slime tile and the movement counter has not run out,
        // the next position is the slime tile.
        SwampTile swampTile = entity.getCurrentSwampTile();
        if (swampTile != null && !swampTile.decrementMovementCounter()) {
            nextPos = swampTile.getPosition();
        } else {
            // Reset the slime tile on the entity
            entity.setCurrentSwampTile(null);
        }
        map.moveTo(entity, nextPos);
    }
}
