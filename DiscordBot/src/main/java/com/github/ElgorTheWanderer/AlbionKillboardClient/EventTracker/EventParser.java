package com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker;

import com.github.ElgorTheWanderer.AlbionKillboardClient.GuildIdRepository;
import com.github.ElgorTheWanderer.AlbionKillboardClient.UrlBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import reactor.netty.http.client.HttpClient;

import java.time.LocalDateTime;

public class EventParser {
    public static void parseLastEvents(GuildIdRepository.GuildInfo guildInfo){


        // While lastEventTimestamp < structure.eventTimeStamp, add to post message.

        String killboardResult = HttpClient.create().get().uri(UrlBuilder.buildTrackerUrl(guildInfo))
                .responseContent().aggregate().asString().block();
        assert killboardResult != null;

        JSONArray eventsArray = new JSONArray(killboardResult);
        for (int i = 0; i < eventsArray.length(); i++) {

            JSONObject event = eventsArray.getJSONObject(i);
            EventStructure structure = new EventStructure();
            structure.eventId = event.getString("EventId");
            structure.battleId = event.getString("BattleId");
            structure.eventTimeStamp = LocalDateTime.parse(event.getString("TimeStamp"));
            structure.numberOfParticipants = event.getJSONArray("Participants").length();
            structure.groupMemberCount = event.getJSONArray("GroupMembers").length();
            structure.totalKillFame = event.getInt("TotalVictimKillFame");

            //TODO - Resume parsing from here.


            guildInfo.listOfLastEvents.put(structure.eventId, structure);
            guildInfo.lastEventTimestamp = structure.eventTimeStamp;
            guildInfo.lastEventId = structure.eventId;
        }
    }
}
