package com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker;

import java.time.LocalDateTime;
import java.util.List;

public class EventStructure {
    public String eventId;
    public String battleId;
    public LocalDateTime eventTimeStamp;
    public Player killer;
    public Player victim;
    public int groupMemberCount;
    public int numberOfParticipants;
    public int totalKillFame;
    public List<Player> participants;
}
