package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface EntityOnMovedAway {
    public void onMovedAway(GameMap map, Entity entity);
}
