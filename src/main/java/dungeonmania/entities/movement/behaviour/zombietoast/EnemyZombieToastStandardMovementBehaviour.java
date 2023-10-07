package dungeonmania.entities.movement.behaviour.zombietoast;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.SwampTile;
import dungeonmania.entities.movement.behaviour.EnemyMovementBehaviour;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class EnemyZombieToastStandardMovementBehaviour implements EnemyMovementBehaviour {
    private Random randGen = new Random();

    @Override
    public void move(GameMap map, Entity entity) {
        Position nextPos;
        List<Position> pos = entity.getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.canMoveTo(entity, p)).collect(Collectors.toList());
        if (pos.size() == 0) {
            nextPos = entity.getPosition();
        } else {
            nextPos = pos.get(randGen.nextInt(pos.size()));
        }

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
