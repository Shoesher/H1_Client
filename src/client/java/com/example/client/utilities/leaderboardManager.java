package com.example.client.utilities;

import com.example.client.constants.formatting;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import org.apache.logging.log4j.core.pattern.TextRenderer;
import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class leaderboardManager {
    private static leaderboardManager board = null;
    Minecraft client = Minecraft.getInstance();
    public int finalPos = 1;

    public void updateLeaderboard(){
        ClientboundPlayerChatPacket cPacket = new ClientboundPlayerChatPacket(
                1, client.player.getUUID(), 1
        );

        client.gui.hud.getChat().getRecentChat()
        List<String> chatHistory = client.getConnection().handlePlayerChat(cPacket);
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