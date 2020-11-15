package com.github.ElgorTheWanderer.BusinessLogic;

import com.github.ElgorTheWanderer.AlbionDataClient.AlbionDataClient;
import com.github.ElgorTheWanderer.AlbionDataClient.ItemPriceTable;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import discord4j.core.object.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class CheckItemPriceProcessor implements CommandProcessor {

    static final String COMMAND_NAME = "!price";
    private final AlbionDataClient albionDataClient;
    private final DiscordManager discordManager;

    public CheckItemPriceProcessor(AlbionDataClient albionDataClient, DiscordManager discordManager) {
        this.albionDataClient = albionDataClient;
        this.discordManager = discordManager;
    }

    private void replyToPriceEvent(Message message) {

        try {
            String itemName = getCommandFromMessage(message.getContent());
            ItemPriceTable result = albionDataClient.findItemPrice(itemName);

            String header = "City name ";
            for (ItemPriceTable.ItemEntry itemEntry : result.entryList) {
                header = header.concat(itemEntry.itemId + " | ");
            }
            header = header.concat("\n");

            List<String> cityNameList = new ArrayList<>();
            for (ItemPriceTable.ItemEntry itemEntry : result.entryList) {
                for (ItemPriceTable.ItemEntry.CityEntry cityEntry : itemEntry.cityEntryList) {
                    cityNameList.add(cityEntry.name);
                }

            }

            String responseBody = "";

            for (String cityName : cityNameList) {
                String cityRow = cityName;

                for (ItemPriceTable.ItemEntry itemEntry : result.entryList) {
                    boolean foundCityEntry = false;
                    for (ItemPriceTable.ItemEntry.CityEntry cityEntry : itemEntry.cityEntryList) {
                        if (cityEntry.name.equals(cityName)) {
                            cityRow = cityRow.concat(cityEntry.minSellPrice + " / " + cityEntry.maxBuyPrice);
                            foundCityEntry = true;
                            break;
                        }
                    }
                    if (!foundCityEntry){
                        cityRow = cityRow.concat("n/a");
                    }
                }
                cityRow = cityRow.concat("\n");
                responseBody = responseBody.concat(cityRow);
            }
            final String responseMessage = header + responseBody;

            message.getChannel().subscribe(channel -> discordManager.sendMessage(responseMessage, channel));
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
        replyToPriceEvent(message);
    }
}
