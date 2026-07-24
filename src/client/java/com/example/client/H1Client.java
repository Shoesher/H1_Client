package com.example.client;

import com.example.client.constants.formatting;
import com.example.client.utilities.chatManager;
import com.example.client.utilities.leaderboardManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import com.example.client.utilities.lapCounter;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.resources.Identifier;

public class H1Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
		//new chatManager().registerCommands();

        ClientReceiveMessageEvents.GAME.register((message, isOverlay) -> {
            //Sends message content to the leaderboard manager list
            if (isOverlay) return;
            leaderboardManager.getInstance().parseLapData(message.getString());
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            //periodic
            if (client.level == null) return;
            if (client.player == null) return;
            lapCounter.getInstance().runLapCounter();
        });

        HudElementRegistry.attachElementBefore(
                //Visuals
                VanillaHudElements.CHAT,
                Identifier.fromNamespaceAndPath("h1client", "race_leaderboard"),
                leaderboardManager.getInstance()::displayLeaderboard
        );
    }
}
