package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import com.example.client.utilities.lapCounter;

public class H1Client implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.level == null) return;
			if (client.player == null) return;
			lapCounter.getInstance().runLapCounter();
		});
	}
}
