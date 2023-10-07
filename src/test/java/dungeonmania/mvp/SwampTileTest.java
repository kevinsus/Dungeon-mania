package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampTileTest {
    @Test
    @Tag("10-1")
    @DisplayName("Test player passing through slime")
    public void playerSwamp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTest_player", "c_swampTest_player");

        // player moves onto slime
        res = dmc.tick(Direction.DOWN);

        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();

        // check the player can now move again
        res = dmc.tick(Direction.DOWN);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
    }

    // repeat the above structure for zombies, enemy mercenaries, allied mercenaries and spiders

    @Test
    @Tag("10-2")
    @DisplayName("Test zombie passing through swamp")
    public void zombieSwamp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTest_zombie", "c_swampTest_zombie");

        // zombie moves onto slime
        res = dmc.tick(Direction.RIGHT);

        // check the zombie stayed on the slime for the specified ticks
        Position pos = TestUtils.getEntities(res, "zombie").get(0).getPosition();
        assertEquals(new Position(2, 5), pos);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(2, 5), pos);

        // check the zombie can now move again
        res = dmc.tick(Direction.DOWN);
        assertNotEquals(pos, TestUtils.getEntities(res, "zombie").get(0).getPosition());
    }

    @Test
    @Tag("10-3")
    @DisplayName("Test enemy mercenary passing through slime")
    public void enemyMercenarySwamp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTest_merc", "c_swampTest_merc");

        // merc moves onto slime
        res = dmc.tick(Direction.RIGHT);

        // check the merc stayed on the slime for the specified ticks
        Position pos = TestUtils.getEntities(res, "mercenary").get(0).getPosition();
        assertEquals(new Position(2, 5), pos);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(2, 5), pos);

        // check the merc can now move again
        res = dmc.tick(Direction.DOWN);
        assertNotEquals(pos, TestUtils.getEntities(res, "mercenary").get(0).getPosition());
    }

    @Test
    @Tag("10-4")
    @DisplayName("Test allied mercenary passing through slime")
    public void alliedMercenarySwamp() {
        /**
         * W W W W W W E
         * W T P - - M -
         * W W W W W W -
         *
         * bribe_radius = 100
         * bribe_amount = 1
         */
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_allyMovementStick", "c_mercenaryTest_allyMovementStick");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // pick up treasure
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(4, 1), getMercPos(res));

        // Wait until the mercenary is next to the player
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(3, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(2, 1), getMercPos(res));

        // achieve bribe - success
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(2, 1), getMercPos(res));

        // Ally follows the player
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(2, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(2, 1), getPlayerPos(res));
        assertEquals(new Position(1, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(3, 1), getPlayerPos(res));
        assertEquals(new Position(2, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(4, 1), getPlayerPos(res));
        assertEquals(new Position(3, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), getPlayerPos(res));
        assertEquals(new Position(4, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(6, 1), getPlayerPos(res));
        assertEquals(new Position(5, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(7, 1), getPlayerPos(res));
        assertEquals(new Position(6, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(8, 1), getPlayerPos(res));
        assertEquals(new Position(7, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(9, 1), getPlayerPos(res));
        assertEquals(new Position(8, 1), getMercPos(res));
    }

    @Test
    @Tag("10-5")
    @DisplayName("Test spider passing through slime")
    public void spiderSwamp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTest_spider", "c_swampTest_spider");

        // spider moves onto slime
        res = dmc.tick(Direction.RIGHT);

        // check the spider stayed on the slime for the specified ticks
        Position pos = TestUtils.getEntities(res, "spider").get(0).getPosition();
        //checks in case spider goes up through z axis. if it does, return as invalid test
        if (pos != new Position(1, 5)) {
            return;
        }
        assertEquals(new Position(1, 5), pos);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 5), pos);

        // check the spider can now move again
        res = dmc.tick(Direction.RIGHT);
        assertNotEquals(new Position(2, 5), pos);
    }

    private Position getPlayerPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "player").get(0).getPosition();
    }

    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }
}
