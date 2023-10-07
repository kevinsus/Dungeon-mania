package dungeonmania.entities.playerState;

import dungeonmania.entities.collectables.potions.Potion;

public interface PlayerState {
    public int triggerNext(int currentTick, Potion nextPotion);
}
