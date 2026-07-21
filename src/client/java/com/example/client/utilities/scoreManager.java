package com.example.client.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public class scoreManager {
    private Minecraft client = Minecraft.getInstance();
    private static scoreManager score = null;
    int playerPoints = 0;

    public void calcPoints(int finalPos){
        playerPoints += pointMap.get(finalPos);
    }

    public void displayPoints(){
        //write data to local json file
        client.getConnection().sendCommand("scoreboard players set " + client.getUser().getName() + " Points: " + playerPoints);
    }

    //key = finishing position
    //value = points
    Map<Integer, Integer> pointMap = new HashMap<>(
        Map.ofEntries(
            Map.entry(1, 20),
            Map.entry(2, 18),
            Map.entry(3, 16),
            Map.entry(4, 14),
            Map.entry(5, 12),
            Map.entry(6, 10),
            Map.entry(7, 8),
            Map.entry(8, 6),
            Map.entry(9, 4),
            Map.entry(10, 2),
            Map.entry(11, 0),
            Map.entry(12, 0)
        )
    );

    public static scoreManager getInstance(){
        if(score == null){
            score = new scoreManager();
        }
        return score;
    }
}