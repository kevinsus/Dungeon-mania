package dungeonmania.entities;

import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwampTile extends Entity implements EntityOnOverlap {
    public static final double DEFAULT_MOVEMENT_FACTOR = 2.0;
    private final double movementFactor;
    private double movementCounter;

    public SwampTile(Position position, double movementFactor) {
        super(position.asLayer(FLOOR_LAYER));
        this.movementFactor = movementFactor;
    }

    public double movementFactor() {
        return movementFactor;
    }

    public void resetMovementCounter() {
        movementCounter = movementFactor;
    }

    public boolean decrementMovementCounter() {
        movementCounter--;
        return movementCounter <= 0;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
            return;
        }
        if (entity instanceof Enemy) {
            if (entity instanceof Mercenary) {
                if (((Mercenary) entity).isAllied())
                    return;
            }
            // Set this slime tile on the entity and start the counter
            entity.setCurrentSwampTile(this);
            this.resetMovementCounter();
        }

    }

}
