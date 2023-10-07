package dungeonmania.entities.enemies.movementutil;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.entities.movement.behaviour.EnemyInvincibilityPotionMovementBehaviour;
import dungeonmania.entities.movement.behaviour.EnemyMovementBehaviour;
import dungeonmania.entities.movement.behaviour.mercenary.EnemyAlliedMercenaryMovementBehaviour;
import dungeonmania.entities.movement.behaviour.mercenary.EnemyInvisibilityPotionMercenaryMovementBehaviour;
import dungeonmania.entities.movement.behaviour.mercenary.EnemyMercenaryStandardMovementBehaviour;
import dungeonmania.map.GameMap;

public class MercenaryMovementUtil {
    private EnemyMovementBehaviour mercenaryMovementBehaviour;

    public MercenaryMovementUtil() {
        mercenaryMovementBehaviour = null;
    }

    public void move(GameMap map, Entity entity, Player player, boolean allied) {
        update(map, allied, player);
        mercenaryMovementBehaviour.move(map, entity);
    }

    private void update(GameMap map, boolean allied, Player player) {
        if (map.getPlayer().getEffectivePotion() instanceof InvisibilityPotion)
            mercenaryMovementBehaviour = new EnemyInvisibilityPotionMercenaryMovementBehaviour();
        else if (allied)
            mercenaryMovementBehaviour = new EnemyAlliedMercenaryMovementBehaviour(player);
        else if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion)
            mercenaryMovementBehaviour = new EnemyInvincibilityPotionMovementBehaviour(player);
        else
            mercenaryMovementBehaviour = new EnemyMercenaryStandardMovementBehaviour(player);
    }
}
