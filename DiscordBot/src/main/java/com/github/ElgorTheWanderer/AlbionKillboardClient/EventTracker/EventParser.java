package com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker;

import com.github.ElgorTheWanderer.AlbionKillboardClient.GuildIdRepository;
import com.github.ElgorTheWanderer.AlbionKillboardClient.UrlBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import reactor.netty.http.client.HttpClient;

import java.time.LocalDateTime;

public class EventParser {
    public static void parseLastEvents(GuildIdRepository.GuildInfo guildInfo) {
        if (guildInfo.listOfLastEvents.size() == 0) {
            String killboardResult = HttpClient.create().get().uri(UrlBuilder.buildTrackerUrl(guildInfo))
                    .responseContent().aggregate().asString().block();
            assert killboardResult != null;
            JSONArray eventsArray = new JSONArray(killboardResult);

            for (int i = 0; i < eventsArray.length(); i++) {
                JSONObject event = eventsArray.getJSONObject(i);
                LocalDateTime eventTimestamp = LocalDateTime.parse(event.getString("TimeStamp"));

                if (eventTimestamp.isBefore(guildInfo.lastEventTimestamp) || eventTimestamp
                        .isEqual(guildInfo.lastEventTimestamp)) {
                    break;
                } else {
                    JSONObject killer = event.getJSONObject("Killer");
                    JSONObject victim = event.getJSONObject("Victim");
                    EventStructure structure = new EventStructure();
                    structure.killer = PlayerParser.parsePlayer(killer);
                    structure.victim = PlayerParser.parsePlayer(victim);
                    structure.eventId = event.getString("EventId");
                    structure.battleId = event.getString("BattleId");
                    structure.eventTimeStamp = eventTimestamp;
                    structure.numberOfParticipants = event.getJSONArray("Participants").length();
                    structure.groupMemberCount = event.getJSONArray("GroupMembers").length();
                    structure.totalKillFame = event.getInt("TotalVictimKillFame");
                    guildInfo.listOfLastEvents.add(structure);
                    guildInfo.lastEventTimestamp = structure.eventTimeStamp;
                    guildInfo.lastEventId = structure.eventId;
                }
            }
        }
    }
}
