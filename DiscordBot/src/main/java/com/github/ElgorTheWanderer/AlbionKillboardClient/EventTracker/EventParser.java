package com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker;

import com.github.ElgorTheWanderer.AlbionKillboardClient.GuildIdRepository;
import com.github.ElgorTheWanderer.AlbionKillboardClient.UrlBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import reactor.netty.http.client.HttpClient;

import java.time.ZonedDateTime;

public class EventParser {

    public static void parseLastEvents(GuildIdRepository.GuildInfo guildInfo) {
            String killboardResult = HttpClient.create().get().uri(UrlBuilder.buildTrackerUrl(guildInfo))
                    .responseContent().aggregate().asString().block();

        System.out.println(UrlBuilder.buildTrackerUrl(guildInfo));

            System.out.println("Events requested");

            assert killboardResult != null;
            JSONArray eventsArray = new JSONArray(killboardResult);

            for (int i = 9; i > (-1); i--) {
                JSONObject event = eventsArray.getJSONObject(i);
                ZonedDateTime eventTimestamp = ZonedDateTime.parse(event.getString("TimeStamp"));

                System.out.println(eventTimestamp);

                System.out.println("Event received");

                if (eventTimestamp.isBefore(guildInfo.lastEventTimestamp) || eventTimestamp
                        .isEqual(guildInfo.lastEventTimestamp)) {

                    System.out.println("Event timestamp: " + eventTimestamp);
                    System.out.println("Guild timestamp: " + guildInfo.lastEventTimestamp);

                    System.out.println("Event is before!");

                } else {
                    JSONObject killer = event.getJSONObject("Killer");
                    JSONObject victim = event.getJSONObject("Victim");
                    EventStructure structure = new EventStructure();
                    structure.killer = PlayerParser.parsePlayer(killer);
                    structure.victim = PlayerParser.parsePlayer(victim);
                    structure.eventId = event.getInt("EventId");
                    structure.battleId = event.getInt("BattleId");
                    structure.eventTimeStamp = eventTimestamp;

                    System.out.println(structure.eventTimeStamp);
                    System.out.println(structure);

                    structure.numberOfParticipants = event.getJSONArray("Participants").length();
                    structure.groupMemberCount = event.getJSONArray("GroupMembers").length();
                    structure.totalKillFame = event.getInt("TotalVictimKillFame");
                    guildInfo.listOfLastEvents.add(structure);

                    System.out.println(guildInfo.lastEventTimestamp);

                    guildInfo.lastEventTimestamp = structure.eventTimeStamp;
                    guildInfo.lastEventId = structure.eventId;

                    System.out.println("Event parsed successfully");

                }
            }
        }
    }

