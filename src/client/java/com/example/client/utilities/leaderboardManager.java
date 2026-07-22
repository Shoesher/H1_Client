package com.example.client.utilities;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.util.ARGB;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Scoreboard;
import java.util.*;

public class leaderboardManager {
    private static leaderboardManager board = null;
    Minecraft client = Minecraft.getInstance();
    public int finalPos = 1;

    public void updateLeaderboard(){
        if(client.level == null || client.player == null) return;

        Scoreboard scoreboard = client.level.getScoreboard();
        Objective objective = scoreboard.getObjective("laps");
        if(objective == null) return; // datapack hasn't loaded/created it yet

        ArrayList<Integer> playerLevels = new ArrayList<>();
        int aheadCount = 0;
        int playerLevel = lapCounter.getInstance().playerLaps;

        for(PlayerInfo racer : client.getConnection().getOnlinePlayers()){
            if(racer.getProfile().id().equals(client.player.getUUID())) continue;
            if(racer.getGameMode().isSurvival()){
                String name = racer.getProfile().name();
                ScoreHolder holder = ScoreHolder.forNameOnly(name);
                int rivalLevel = scoreboard.getOrCreatePlayerScore(holder, objective).get();
                playerLevels.add(rivalLevel);
            }
        }

        for(int rivalLevel : playerLevels){
            if(rivalLevel >= playerLevel){
                aheadCount++;
            }
        }

        getPosFromIndex(aheadCount);
    }

    private void getPosFromIndex(int aheadCount){
        this.finalPos = 1 + aheadCount;
    }

    public void displayLeaderboard(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker){
        String text1 = "Position: " + finalPos;
        String text2 = "Lap: " + lapCounter.getInstance().playerLaps + "/" + lapCounter.getInstance().targetLaps;
        int x1 = 10;
        int y1 = 10;
        int y2 = 30;
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