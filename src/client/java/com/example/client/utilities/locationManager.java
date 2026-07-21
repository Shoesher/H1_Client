package com.example.client.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import com.example.client.constants.locations;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;

public class locationManager {
    private static locationManager locator = null;
    Minecraft client = Minecraft.getInstance();

    public void teleportLobby(){
        client.getConnection().sendCommand("teleport " + locations.lobbyX + " " + locations.lobbyY + " " + locations.lobbyZ);
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
