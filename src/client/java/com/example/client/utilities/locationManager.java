package com.example.client.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import com.example.client.constants.locations;

public class locationManager {
    private static locationManager locator = null;
    Minecraft client = Minecraft.getInstance();

    public void teleportLobby(){
        client.player.teleportTo(locations.lobbyX,locations.lobbyY,locations.lobbyZ);
    }

    public void teleportMap(){
        //check if the most recent message the user made included a map code before using that code to teleport to that map location
        //feature will come after the coords are recorded
    }

    public static locationManager getInstance(){
        if(locator == null){
            locator = new locationManager();
        }
        return locator;
    }
}
