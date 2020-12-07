package com.github.ElgorTheWanderer.AlbionKillboardClient;

public class AlbionKillboardClientImpl implements AlbionKillboardClient{

    //TODO Start here.
    // - Save timestamp to GuildIdRepository, when new guild tracking is added.
    // - For first ever request save LastEvent Id for further validation.
    // - KillboardScheduler calls endpoint for each GuildIdRepository guild once in 5 minutes to get 10 recent events.
    // - Search for GuildIdRepository.LastEvent Id and timestamp.
    // - If LastEvent timestamp is older than recent events, post these events to channel.
    // - If LastEvent Id is not found, request next 10 events with offset.
    // - Post events sorted from oldest to newest.

    @Override
    public void addGuildTracking(String guildName) {


    }
}
