package com.example.client.utilities;

import com.example.client.constants.formatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.Minecraft;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class leaderboardManager {
    private static leaderboardManager board = null;
    Minecraft client = Minecraft.getInstance();
    public int finalPos = 0;

    public void updateLeaderboard(){
        List<String> chatHistory = client.gui.hud.getChat().getRecentChat();
        if(chatHistory.isEmpty()) return;

        LapData selfEntry = parseLapData(chatHistory.get(0));
        if(selfEntry == null) return;

        int userLap = selfEntry.lap();
        Set<String> seenUsers = new HashSet<>();
        seenUsers.add(selfEntry.username());
        int aheadCount = 0;

        for(int i = 1; i < chatHistory.size(); i++){
            LapData entry = parseLapData(chatHistory.get(i));
            if(entry == null) continue;

            if(seenUsers.contains(entry.username())){

                break;
            }
            seenUsers.add(entry.username());

            if(entry.lap() >= userLap){
                aheadCount++;
            }
        }

        getPosFromIndex(aheadCount);
    }

    private LapData parseLapData(String rawMessage){
        if(!rawMessage.startsWith(formatting.PREFIX)) return null;
        String[] parts = rawMessage.substring(formatting.PREFIX.length()).split(formatting.SEPERATOR);
        if(parts.length != 2) return null;
        try {
            return new LapData(parts[0], Integer.parseInt(parts[1]));
        } catch (NumberFormatException e){
            return null;
        }
    }

    private record LapData(String username, int lap){}

    private void getPosFromIndex(int aheadCount){
        finalPos = 1 + aheadCount;
    }

    public void displayLeaderboard(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker){
        String text = "Position: " + finalPos;
        int x = 10;
        int y = 10;

        graphics.text(client.font, text, x, y, 0xFFFFFF);
    }

    public static leaderboardManager getInstance(){
        if(board == null){
            board = new leaderboardManager();
        }
        return board;
    }
}