package com.github.ElgorTheWanderer.BusinessLogic;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManagerObserver;
import discord4j.core.object.entity.Message;

public class DiscordManagerObserverImpl implements DiscordManagerObserver {

    private final AlbionClient albionClient;
    private final DiscordManager discordManager;
    private final String[] commandList = {"!user ", "!add ", "!remove ", "!price "};

    public DiscordManagerObserverImpl(AlbionClient albionClient, DiscordManager discordManager) {
        this.albionClient = albionClient;
        this.discordManager = discordManager;
    }

    private String getCommand(Message message) {
        String command = null;
        for (int i = 0; i < commandList.length; i++) {
            if (message.getContent().toLowerCase().startsWith(commandList[i])) {
                command = commandList[i];
            }
        }
        return command;
    }


    private void processIncomingMessage(Message message) {
        if (canProcessMessage(message) == false) {
            return;
        }
        if (getCommand(message) == "!user ") {
            System.out.println("User command processed");                                                               //Delete this line.
            UserProcessor userProcessor = new UserProcessor(albionClient, discordManager);
            userProcessor.processUserCommand(message);

        }else if(getCommand(message) == "!add "){
            System.out.println("Add command processed");                                                                //Delete this line.
            AddProcessor addProcessor = new AddProcessor(albionClient, discordManager);
            addProcessor.processAddCommand(message);


        }else if(getCommand(message) == "!remove "){
            System.out.println("Remove command processed");                                                             //Delete this line.
            RemoveProcessor removeProcessor = new RemoveProcessor(albionClient, discordManager);
            removeProcessor.processRemoveCommand(message);
        }else if(getCommand(message) == "!price "){
            System.out.println("Price command processed");                                                              //Delete this line.
            PriceProcessor priceProcessor = new PriceProcessor(albionClient, discordManager);
            priceProcessor.processPriceCommand(message);
        }
    }

    //Combine with getCommand?
    private boolean canProcessMessage(Message message) {
        boolean handleable = false;
        for (int i = 0; i < commandList.length; i++) {
            if (message.getContent().toLowerCase().startsWith(commandList[i])) {
                handleable = true;
            }
        }
        System.out.println(handleable);                                                                                 //Delete this line.
        if(handleable == false){                                                                                        //Delete this line.
            System.out.println("Invalid command");                                                                      //Delete this line.
        }
        return handleable;
    }

    @Override
    public void onMessageReceived(Message message) {
        processIncomingMessage(message);
    }
}