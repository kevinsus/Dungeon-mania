package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.Potion;

public class BaseState implements PlayerState {
    private Player playerContext;

    public BaseState(Player playerContext) {
        this.playerContext = playerContext;
    }

    @Override
    public int triggerNext(int currentTick, Potion next) {
        if (next == null) {
            playerContext.changeState(new BaseState(playerContext));
            return currentTick;
        }

        playerContext.changeState(next instanceof InvincibilityPotion ? new InvincibleState(playerContext)
                : new InvisibleState(playerContext));
        return currentTick + next.getDuration();
    }
}
