package dungeonmania.goals.leaf;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Exit;
import dungeonmania.entities.Player;
import dungeonmania.goals.Goal;
import dungeonmania.util.Position;

public class ExitGoal implements Goal {
    @Override
    public boolean achieved(Game game) {
        Player character = game.getPlayer();
        Position pos = character.getPosition();
        List<Exit> exits = game.getMap().getEntities(Exit.class);
        if (exits == null || exits.isEmpty())
            return false;

        return exits.stream().map(Entity::getPosition).anyMatch(pos::equals);
    }

    @Override
    public String toString(Game game) {
        return achieved(game) ? "" : ":exit";
    }
}
