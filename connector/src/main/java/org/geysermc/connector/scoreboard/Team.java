package org.geysermc.connector.scoreboard;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class Team {
    private final Scoreboard scoreboard;
    private final String id;

    private UpdateType updateType = UpdateType.ADD;
    private String name;
    private String prefix;
    private String suffix;
    private Set<String> entities = new HashSet<>();


    public Team(Scoreboard scoreboard, String id) {
        this.scoreboard = scoreboard;
        this.id = id;
    }

    public void addEntities(String... names) {
        List<String> added = new ArrayList<>();
        for (String name : names) {
            if (!entities.contains(name)) {
                entities.add(name);
                added.add(name);
            }
        }
        setUpdateType(UpdateType.UPDATE);
        for (Objective objective : scoreboard.getObjectives().values()) {
            for (Score score : objective.getScores().values()) {
                if (added.contains(score.getName())) {
                    score.setTeam(this);
                }
            }
        }
    }

    public void removeEntities(String... names) {
        for (String name : names) {
            entities.remove(name);
        }
        setUpdateType(UpdateType.UPDATE);
    }
}
