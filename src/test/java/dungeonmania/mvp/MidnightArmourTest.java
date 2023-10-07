package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;

public class MidnightArmourTest {
    @Test
    @Tag("18-1")
    @DisplayName("Test InvalidActionException is raised when the player "
            + "does not have sufficient items to build a midnight armour")
    public void buildInvalidActionException() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        dmc.newGame("d_MidnightArmourTest_BuildInvalidArgumentException",
                "c_MidnightArmourTest_BuildInvalidArgumentException");

        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
    }

    @Test
    @Tag("18-2")
    @DisplayName("Test building a midnight armour with no zombies")
    public void buildMidnightArmourNoZombies() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_MidnightArmourTest_BuildMidnightArmourNoZombies",
                "c_MidnightArmourTest_BuildMidnightArmourNoZombies");

        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getEntities(res, "zombie_toast_spawner").size());

        // Pick up Sword x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Pick up Sun Stone x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Midnight Armour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("18-3")
    @DisplayName("Test building a midnight armour with zombies")
    public void buildMidnightArmourZombies() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_MidnightArmourTest_BuildMidnightArmourZombies",
                "c_MidnightArmourTest_BuildMidnightArmourZombies");

        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());

        // Pick up Sword x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Pick up Sun Stone x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Midnight Armour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());

        // Cannot build armour because zombie exist
        assertThrows(IllegalArgumentException.class, () -> dmc.build("sword"));
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());

        // Materials used in construction still appear in inventory
        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("18-4")
    @DisplayName("Test midnight armour reduces enemy attack")
    public void testMidnightArmourReducesEnemyAttack() throws InvalidActionException {
        DungeonManiaController controller;
        controller = new DungeonManiaController();
        String config = "c_MidnightArmourTest_armourEffect";
        DungeonResponse res = controller.newGame("d_MidnightArmourTest_armourEffect", config);

        // Create Midnight armour using sword and sun stone
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        res = controller.build("midnight_armour");

        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);

        BattleResponse battle = res.getBattles().get(0);
        RoundResponse firstRound = battle.getRounds().get(0);

        // Assumption: Armour effect calculation to reduce damage makes enemyAttack =
        // enemyAttack - armour effect
        int enemyAttack = Integer.parseInt(TestUtils.getValueFromConfigFile("spider_attack", config));
        int armourEffect = Integer.parseInt(TestUtils.getValueFromConfigFile("midnight_armour_defence", config));
        int expectedDamage = (enemyAttack - armourEffect) / 10;
        // Delta health is negative so take negative here
        assertEquals(expectedDamage, -firstRound.getDeltaCharacterHealth(), 0.001);
    }

    @Test
    @Tag("18-5")
    @DisplayName("Test midnight armour increase player attack")
    public void testMidnightArmourIncreasesPlayerAttack() throws InvalidActionException {
        DungeonManiaController controller;
        controller = new DungeonManiaController();
        String config = "c_MidnightArmourTest_armourEffect";
        DungeonResponse res = controller.newGame("d_MidnightArmourTest_armourAttackEffect", config);

        // Create Midnight armour using sword and sun stone
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        res = controller.build("midnight_armour");

        // Battle the zombie
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);
        List<BattleResponse> battles = res.getBattles();
        BattleResponse battle = battles.get(0);

        // This is the attack without the sword
        double playerBaseAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("player_attack", config));
        double swordAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("midnight_armour_attack", config));

        RoundResponse firstRound = battle.getRounds().get(0);

        assertEquals((playerBaseAttack + swordAttack) / 5, -firstRound.getDeltaEnemyHealth(), 0.001);
    }
}
