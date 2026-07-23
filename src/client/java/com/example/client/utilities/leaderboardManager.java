package com.example.client.utilities;

import com.example.client.constants.formatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ARGB;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class leaderboardManager {
    private static leaderboardManager board = null;
    Minecraft client = Minecraft.getInstance();
    public int finalPos = 1;
    public List<LapData> lapHistory = new ArrayList<>();

    private Pattern pattern = Pattern.compile("<([^>]+)>.*:(\\d+)");


    public void updateLeaderboard(){

    }

    public void clearLapLogs(){
        lapHistory.clear();
    }

    public void parseLapData(String rawMessage){
        Matcher matcher = pattern.matcher(rawMessage);
        if(matcher.matches()){
            String player = matcher.group(1);
            int lapCount = Integer.parseInt(matcher.group(2));

            lapHistory.add(new LapData(player, lapCount));
        }

//        for(LapData lap : lapHistory){
//            System.out.println("Name: " + lap.name);
//            System.out.println("Lap: " + lap.lap);
//        }
    }

    public record LapData(String name, int lap) {}

    private void getPosFromIndex(int aheadCount){
        finalPos += aheadCount;
    }

    public void displayLeaderboard(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker){
        String text1 = "Position: " + finalPos;
        String text2 = "Lap: " + lapCounter.getInstance().playerLaps + "/" + lapCounter.getInstance().targetLaps;
        int x1 = 10;
        int y1 = 10;
        int y2 = 30;

        // 0xFFFFFF was RGB-only and would render invisible now - needs an alpha channel
        graphics.text(client.font, text1, x1, y1, ARGB.color(255, 248, 205, 2));
        graphics.text(client.font, text2, x1, y2, ARGB.color(255, 255, 190, 125));
    }

    public static leaderboardManager getInstance(){
        if(board == null){
            board = new leaderboardManager();
        }
        return board;
    }
}