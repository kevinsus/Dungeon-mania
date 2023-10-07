package dungeonmania.goals;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.goals.composite.AndGoal;
import dungeonmania.goals.composite.OrGoal;
import dungeonmania.goals.leaf.BouldersGoal;
import dungeonmania.goals.leaf.EnemiesGoal;
import dungeonmania.goals.leaf.ExitGoal;
import dungeonmania.goals.leaf.TreasureGoal;

public class GoalFactory {
    public static Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        JSONArray subgoals;
        switch (jsonGoal.getString("goal")) {
        case "AND":
            subgoals = jsonGoal.getJSONArray("subgoals");
            return new AndGoal(createGoal(subgoals.getJSONObject(0), config),
                    createGoal(subgoals.getJSONObject(1), config));
        case "OR":
            subgoals = jsonGoal.getJSONArray("subgoals");
            return new OrGoal(createGoal(subgoals.getJSONObject(0), config),
                    createGoal(subgoals.getJSONObject(1), config));
        case "exit":
            return new ExitGoal();
        case "boulders":
            return new BouldersGoal();
        case "treasure":
            int treasureGoal = config.optInt("treasure_goal", 1);
            return new TreasureGoal(treasureGoal);
        case "enemies":
            int enemyGoal = config.optInt("enemy_goal", 0);
            return new EnemiesGoal(enemyGoal);
        default:
            return null;
        }
    }
}
