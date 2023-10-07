package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface EntityOnOverlap {
    public void onOverlap(GameMap map, Entity entity);
}
