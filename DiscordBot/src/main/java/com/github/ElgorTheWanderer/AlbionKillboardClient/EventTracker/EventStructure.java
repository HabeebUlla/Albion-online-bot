package com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class EventStructure {
    public int eventId;
    public int battleId;
    public ZonedDateTime eventTimeStamp;
    public Player killer;
    public Player victim;
    public int groupMemberCount;
    public int numberOfParticipants;
    public int totalKillFame;
    public List<Player> participants;
}
