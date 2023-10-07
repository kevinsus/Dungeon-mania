package dungeonmania.task2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class EnemiesGoalsTest {
    @Test
    @Tag("1-1")
    @DisplayName("Test achieving basic enemy_goal goal (enemy_goal=3)")
    public void basicEnemyGoal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_goalsTest_enemy_goal", "c_task2_goalsTest_highPlayerHealth");

        // four enemies in a row...

        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        res = dmc.tick(Direction.RIGHT);
        assertEquals("", TestUtils.getGoals(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("1-2")
    @DisplayName("Test achieving destroy all spawners (enemy_goal=0)")
    public void basicDestroySpawners() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_goalsTest_basicSpawners",
                "c_task2_goalsTest_highPlayerHealth_zero_enemy_goal");
        String spawnerIdA = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();
        String spawnerIdB = TestUtils.getEntities(res, "zombie_toast_spawner").get(1).getId();
        String spawnerIdC = TestUtils.getEntities(res, "zombie_toast_spawner").get(2).getId();

        // pick up sword, destroy spawner then move one unit to the right
        res = dmc.tick(Direction.RIGHT);
        res = assertDoesNotThrow(() -> dmc.interact(spawnerIdA));
        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // pick up sword, destroy spawner then move one unit to the right
        res = dmc.tick(Direction.RIGHT);
        res = assertDoesNotThrow(() -> dmc.interact(spawnerIdC));
        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // pick up sword then destroy spawner
        res = dmc.tick(Direction.RIGHT);
        res = assertDoesNotThrow(() -> dmc.interact(spawnerIdB));
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("1-3")
    @DisplayName("Test not achieved if not all spawners are destroyed (enemy_goal=3)")
    public void nonAchievedInaccessibleSpawner() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_goalsTest_inaccessibleSpawner",
                "c_task2_goalsTest_highPlayerHealth");

        for (int i = 0; i < 7; i++) {
            res = dmc.tick(Direction.RIGHT);
            assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        }
    }

    @Test
    @Tag("1-4")
    @DisplayName("Test achieving all enemies killed and spawners destroyed")
    public void allEnemiesKilledThenSpawnersDestroyed() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_goalsTest_enemiesFollowedBySpawner",
                "c_task2_goalsTest_highPlayerHealth");
        String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

        // kill all enemies satisfying enemy_goals in the process
        for (int i = 0; i < 6; i++) {
            res = dmc.tick(Direction.RIGHT);
            assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        }

        // pick up sword and destroy spawner
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = assertDoesNotThrow(() -> dmc.interact(spawnerId));
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("1-5")
    @DisplayName("Test achieved enemy_goal=0 and no spawners")
    public void enemyGoalZeroAndNoSpawners() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_goalsTest_noSpawner", "c_task2_goalsTest_basic");

        res = dmc.tick(Direction.RIGHT);
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("1-6")
    @DisplayName("Test achieved all enemies and spawner destroyed THEN exit")
    public void enemyGoalThenExit() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_enemiesGoal_enemiesThenSpawners",
                "c_task2_enemiesGoal_highPlayerHealth_highEnemyGoal");
        String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

        // kill enemies
        for (int i = 0; i < 6; i++) {
            res = dmc.tick(Direction.RIGHT);
            assertTrue(TestUtils.getGoals(res).contains(":exit"));
            assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        }

        // pick up sword
        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // destroy spawner
        res = assertDoesNotThrow(() -> dmc.interact(spawnerId));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));

        // step right
        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // step on exit
        res = dmc.tick(Direction.RIGHT);
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("1-7")
    @DisplayName("Test not achieved exit THEN all enemies and spawner destroyed")
    public void exitThenEnemyGoal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_enemiesGoal_enemiesThenExitThenSpawner",
                "c_task2_goalsTest_highPlayerHealth");
        String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

        // kill enemies
        for (int i = 0; i < 3; i++) {
            res = dmc.tick(Direction.RIGHT);
            assertTrue(TestUtils.getGoals(res).contains(":exit"));
            assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        }

        // pick up sword
        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // step on exit
        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // destroy spawner
        res = assertDoesNotThrow(() -> dmc.interact(spawnerId));
        assertNotEquals("", TestUtils.getGoals(res));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        assertEquals("", TestUtils.getGoals(res));
    }

}
