package roles;

import static actions.Actions.Players;

public class Spy extends Data {

    public void Process(int Spy) {

        player = Players.get(Spy);
        if (!player.jailed && !player.blocked) {
            player.activity.add("SpyVisits");
        }

    }

}