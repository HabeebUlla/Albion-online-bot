package com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker;

public class KillboardEventFormatter {
    public static String messageContent(EventStructure event) {
        return ("\nKiller: " + event.killer.name + "\nVictim: " + event.victim.name
                + "\nFame gained: " + (event.totalKillFame / event.groupMemberCount) + "\nParticipants: "
                + event.numberOfParticipants + "\nTimestamp: " + event.eventTimeStamp + "\n");
    }
}
