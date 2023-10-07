package dungeonmania.entities.enemies.movementutil;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.movement.behaviour.EnemyInvincibilityPotionMovementBehaviour;
import dungeonmania.entities.movement.behaviour.EnemyMovementBehaviour;
import dungeonmania.entities.movement.behaviour.zombietoast.EnemyZombieToastStandardMovementBehaviour;
import dungeonmania.map.GameMap;

public class ZombieToastMovementUtil {
    private EnemyMovementBehaviour enemyMovementBehaviour;

    public ZombieToastMovementUtil() {
        this.enemyMovementBehaviour = null;
    }

    public void move(GameMap map, Entity entity, Player player) {
        update(map, player);
        enemyMovementBehaviour.move(map, entity);
    }

    private void update(GameMap map, Player player) {
        if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            enemyMovementBehaviour = new EnemyInvincibilityPotionMovementBehaviour(player);
        } else {
            enemyMovementBehaviour = new EnemyZombieToastStandardMovementBehaviour();
        }
    }
}
