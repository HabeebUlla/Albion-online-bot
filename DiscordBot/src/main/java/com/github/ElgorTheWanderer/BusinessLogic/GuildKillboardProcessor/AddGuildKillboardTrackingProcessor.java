package com.github.ElgorTheWanderer.BusinessLogic.GuildKillboardProcessor;

import com.github.ElgorTheWanderer.AlbionKillboardClient.AlbionKillboardClient;
import com.github.ElgorTheWanderer.AlbionKillboardClient.GuildIdRepository;
import com.github.ElgorTheWanderer.BusinessLogic.CommandProcessor;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class AddGuildKillboardTrackingProcessor implements CommandProcessor {

    public static final String COMMAND_NAME = "!add";
    private final AlbionKillboardClient albionKillboardClient;
    private final DiscordManager discordManager;


    public AddGuildKillboardTrackingProcessor(AlbionKillboardClient albionKillboardClient, DiscordManager discordManager) {
        this.albionKillboardClient = albionKillboardClient;
        this.discordManager = discordManager;
    }

    private void replyToAddEvent(Message message) {

        try {
            String guildName = getCommandFromMessage(message.getContent());
            MessageChannel channelId = message.getChannel().block();
            boolean uniqueGuild = true;
            for(GuildIdRepository.GuildInfo guild : GuildIdRepository.guildList){
                if(guild.guildName.equals(guildName) && guild.channelId == channelId){
                    message.getChannel().subscribe(channel -> discordManager.sendMessage("Guild " + guildName
                            + " already tracked.", channel));
                    uniqueGuild = false;
                }
            }
            if(uniqueGuild) {
                String guildId = albionKillboardClient.addGuildTracking(guildName, channelId);

                if (guildId != null) {
                    message.getChannel().subscribe(channel -> discordManager.sendMessage("Guild " + guildName
                            + " is being tracked.\n" + "GuildID: " + guildId, channel));
                } else {
                    message.getChannel().subscribe(channel -> discordManager.sendMessage("There is no such guild.", channel));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            String s = "Error: " + e.getMessage();
            message.getChannel().subscribe(channel -> discordManager.sendMessage(s, channel));
        }
    }

    private String getCommandFromMessage(String messageContent) {
        return messageContent.substring(COMMAND_NAME.length()).trim();
    }

    @Override
    public void processCommand(Message message) {
        replyToAddEvent(message);
    }
}