package com.example.client.utilities;

import com.example.client.constants.formatting;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.Minecraft;
import java.util.regex.Pattern;

public class chatManager {
    Minecraft client = Minecraft.getInstance();
    private static Pattern scoreboardTemplate = Pattern.compile(formatting.SCORECOMMAND, Pattern.CASE_INSENSITIVE);
    private static String lapTemplate = formatting.PREFIX;

    private static boolean authenticateCommand(String command){
        if(scoreboardTemplate.matcher(command.trim()).matches()){
            return false;
        }
        return true;
    }

    private static boolean authenticateMessage(String message){
        if(message.startsWith(lapTemplate)){
            return false;
        }
        return true;
    }



    public void registerCommands(){
        ClientSendMessageEvents.ALLOW_COMMAND.register(chatManager::authenticateCommand);
        ClientSendMessageEvents.ALLOW_CHAT.register(chatManager::authenticateMessage);
    }
}
