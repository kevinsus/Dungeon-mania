package dungeonmania.entities.movement.behaviour.mercenary;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movement.behaviour.EnemyMovementBehaviour;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class EnemyInvisibilityPotionMercenaryMovementBehaviour implements EnemyMovementBehaviour {
    public void move(GameMap map, Entity entity) {
        Position nextPos;
        Random randGen = new Random();
        List<Position> pos = entity.getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.canMoveTo(entity, p)).collect(Collectors.toList());
        if (pos.size() == 0) {
            nextPos = entity.getPosition();
            map.moveTo(entity, nextPos);
        } else {
            nextPos = pos.get(randGen.nextInt(pos.size()));
            map.moveTo(entity, nextPos);
        }
        map.moveTo(entity, nextPos);
    }
}
