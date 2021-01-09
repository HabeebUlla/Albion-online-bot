package com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EventStructure {
    //TODO Store parsed kill event info.
    public String eventId;
    public String battleId;
    public LocalDateTime eventTimeStamp;
    public Player killer;
    public Player victim;
    public int groupMemberCount;
    public int numberOfParticipants;
    public int totalKillFame;
    public List<Player> participants;

    public static class Player{
        public double averageItemPower;
        public String name;
        public String Id;
        public String guildName;
        public String guildId;
        public int killFame;
        public int deathFame;
        Map<String, String> equipment;
        Map<String, String> inventory;
    }
}
