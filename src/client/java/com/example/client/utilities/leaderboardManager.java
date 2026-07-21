package com.example.client.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ArrayListDeque;

public class leaderboardManager {
    private static leaderboardManager board = null;
    Minecraft client = Minecraft.getInstance();
    ArrayListDeque<String> chatHistory = client.gui.hud.getChat().getRecentChat();

    public int finalPos = 0;

    public void updateLeaderboard(){
        //loop through the lap logs until a duplicate is found
        //count how many entries -1 are on the same lap as you
        //display

        //i=1 to skip the users own entry
        int equalEntries = 0;
        int userLap = Integer.parseInt(chatHistory.get(0).replaceFirst("kgFBdts154d", ""));
        for (int i = 1; i < chatHistory.size(); i++){
            String rawLapData = chatHistory.get(i).replaceFirst("kgFBdts154d", "");
            int lapData = Integer.parseInt(rawLapData);
            if(lapData == userLap){
                equalEntries += 1;
            }
            //line that will break the for loop if a duplicate is found
            if() break;
        }
        getPosFromIndex(equalEntries);
    }

    private void getPosFromIndex(int equalEntries){
        finalPos = 1 + equalEntries;
    }

    public void displayLeaderboard(){
        //Use the position map to display positions on a GUI
        //Update periodically
        
    }

    //Could potentially read individual positions through chat

    public static leaderboardManager getInstance(){
        if(board == null){
            board = new leaderboardManager();
        }
        return board;
    }
}
