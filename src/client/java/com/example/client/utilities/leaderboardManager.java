package com.example.client.utilities;

import com.example.client.constants.formatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ARGB;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class leaderboardManager {
    private static leaderboardManager board = null;
    Minecraft client = Minecraft.getInstance();
    public int finalPos = 1;
    private final Map<String, Integer> latestLapByUser = new LinkedHashMap<>();

    private final Pattern pattern = Pattern.compile(
            "<([^>]+)>\\s*" + Pattern.quote(formatting.PREFIX) + Pattern.quote(formatting.SEPERATOR) + "(\\d+)"
    );

    private leaderboardManager(){}

    public void parseLapData(String rawMessage){
        Matcher matcher = pattern.matcher(rawMessage);
        if(!matcher.find()) return;

        String player = matcher.group(1);
        int lapCount = Integer.parseInt(matcher.group(2));

        latestLapByUser.put(player, lapCount);
        updateLeaderboard();
    }

    private void updateLeaderboard(){
        String selfUsername = client.getUser().getName();
        Integer userLap = latestLapByUser.get(selfUsername);
        if(userLap == null) return;

        int aheadCount = 0;
        for(Map.Entry<String, Integer> entry : latestLapByUser.entrySet()){
            if(entry.getKey().equals(selfUsername)) continue;
            if(entry.getValue() >= userLap){
                aheadCount++;
            }
        }
        finalPos = 1 + aheadCount;
    }

    public void clearLapLogs(){
        latestLapByUser.clear();
        finalPos = 1;
    }

    public void displayLeaderboard(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker){
        String text1 = "Position: " + finalPos;
        String text2 = "Lap: " + lapCounter.getInstance().playerLaps + "/" + lapCounter.getInstance().targetLaps;

        graphics.text(client.font, text1, 10, 10, ARGB.color(255, 248, 205, 2));
        graphics.text(client.font, text2, 10, 30, ARGB.color(255, 255, 190, 125));
    }

    public static leaderboardManager getInstance(){
        if(board == null){
            board = new leaderboardManager();
        }
        return board;
    }
}