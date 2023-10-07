package dungeonmania.entities.movement.behaviour.spider;

import java.util.List;

import dungeonmania.entities.Boulder;
import dungeonmania.entities.Entity;
import dungeonmania.entities.SwampTile;
import dungeonmania.entities.movement.behaviour.EnemyMovementBehaviour;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class EnemySpiderStandardMovementBehaviour implements EnemyMovementBehaviour {
    private List<Position> movementTrajectory;
    private int nextPositionElement;
    private boolean forward;

    public EnemySpiderStandardMovementBehaviour(List<Position> movementTrajectory, int nextPositionElement,
            boolean forward) {
        this.movementTrajectory = movementTrajectory;
        this.nextPositionElement = nextPositionElement;
        this.forward = forward;
    }

    public void move(GameMap map, Entity entity) {
        Position nextPos = movementTrajectory.get(nextPositionElement);
        List<Entity> entities = map.getEntities(nextPos);
        if (entities != null && entities.size() > 0 && entities.stream().anyMatch(e -> e instanceof Boulder)) {
            forward = !forward;
            updateNextPosition(2);
        }
        nextPos = movementTrajectory.get(nextPositionElement);
        entities = map.getEntities(nextPos);
        if (entities.size() == 0 || entities.stream().allMatch(e -> e.canMoveOnto(map, entity))) {

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
            updateNextPosition();
        }
    }

    private void updateNextPosition() {
        if (forward) {
            nextPositionElement = (nextPositionElement + 1) % 8;
        } else {
            nextPositionElement = (nextPositionElement - 1 + 8) % 8;
        }
    }

    private void updateNextPosition(int steps) {
        for (int i = 0; i < steps; i++)
            updateNextPosition();
    }
}
