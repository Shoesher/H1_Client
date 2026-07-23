package com.example.client;

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
//		new chatManager().registerCommands();

		// Triggers for system messages (e.g., server announcements, warps, mini-game alerts)
		ClientReceiveMessageEvents.GAME.register((message, isOverlay) -> {
			if (!isOverlay) {
				//Prints out the contents of the chat message
				System.out.println(message.getSiblings().getFirst().getString());
				leaderboardManager.getInstance().parseLapData(message.getSiblings().getFirst().getString());
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.level == null) return;
			if (client.player == null) return;
			lapCounter.getInstance().runLapCounter();
		});

		HudElementRegistry.attachElementBefore(
				VanillaHudElements.CHAT,
				Identifier.fromNamespaceAndPath("h1client", "race_leaderboard"),
				leaderboardManager.getInstance()::displayLeaderboard
		);

		ClientReceiveMessageEvents.GAME.register((message, isOverlay) -> {
			if (!isOverlay) {

			}
		});
	}
}
