package dungeonmania.goals.composite;

import dungeonmania.Game;
import dungeonmania.goals.Goal;
import dungeonmania.goals.leaf.ExitGoal;

public class AndGoal implements Goal {
    private boolean conjunctionAchievedLock;
    private Goal goal1;
    private Goal goal2;

    public AndGoal(Goal goal1, Goal goal2) {
        this.goal1 = goal1;
        this.goal2 = goal2;
        conjunctionAchievedLock = false;
    }

    @Override
    public boolean achieved(Game game) {
        if (goal1 instanceof ExitGoal)
            setLock(game, (ExitGoal) goal1, goal2);
        else if (goal2 instanceof ExitGoal)
            setLock(game, (ExitGoal) goal2, goal1);

        return !conjunctionAchievedLock && goal1.achieved(game) && goal2.achieved(game);
    }

    @Override
    public String toString(Game game) {
        return achieved(game) ? "" : "(" + goal1.toString(game) + " AND " + goal2.toString(game) + ")";
    }

    private void setLock(Game game, ExitGoal exitGoal, Goal otherGoal) {
        if (conjunctionAchievedLock && !exitGoal.achieved(game))
            conjunctionAchievedLock = false;

        if (exitGoal.achieved(game) && !otherGoal.achieved(game)) {
            conjunctionAchievedLock = true;
        }
    }
}
