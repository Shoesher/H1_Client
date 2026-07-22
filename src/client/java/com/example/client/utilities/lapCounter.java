package com.example.client.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import com.example.client.constants.blocksMap;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;

public class lapCounter {
    private static lapCounter laps = null;

    public int playerLaps = 0;
    public int targetLaps = 0;
    private Minecraft client = Minecraft.getInstance();
    private blocksMap mainBlockMap = blocksMap.getInstance();
    private locationManager locator = locationManager.getInstance();
    private scoreManager score = scoreManager.getInstance();
    private leaderboardManager board = leaderboardManager.getInstance();
    //The horse elevates the player by more than one block
    private Block standingBlock;
    private AtomicBoolean isOnCooldown = new AtomicBoolean(false);
    private Timer timer = new Timer();
    private int targetTicks = 100;
    private int ticksCounter = targetTicks + 1;

    private void coordsLapCounter(BlockPos location){
        //Use map to determine target laps
        //Count a lap if the player on the block and the finish line is not on cooldown
        //Update active lap count, run leaderboard manager
        //If the active laps is greater than the target laps, run score manager, run location manager
    }

    public void lapCooldownManager(){
        //flips a variable based on timing
        if(ticksCounter > targetTicks){
            isOnCooldown.set(false);
        }
        else{
            isOnCooldown.set(true);
        }
    }

    private void blockLapCounter(){
        targetLaps = mainBlockMap.raceBlocks.get(standingBlock);
        if (playerLaps < targetLaps){
            if(getValidBlock() && !isOnCooldown.get()) {
                playerLaps += 1;
                ticksCounter = 0;
                client.getConnection().sendCommand("trigger laps set " + playerLaps);
                board.updateLeaderboard();
            }
        }
        else{
            locator.teleportLobby();
            score.calcPoints(board.finalPos);
            score.displayPoints();
            resetLapCounter();
        }
    }

    private void resetLapCounter(){
        playerLaps = 0;
        targetLaps = 0;
        isOnCooldown.set(false);
        ticksCounter = targetTicks + 1;
        client.getConnection().sendCommand("trigger laps set 0");
    }

    private boolean getValidBlock(){
        boolean onValidBlock = false;
        BlockPos targetBlock = client.player.blockPosition().below(1);
        standingBlock = client.level.getBlockState(targetBlock).getBlock();
        return mainBlockMap.raceBlocks.containsKey(standingBlock);
    }

    //run periodically
    public void runLapCounter(){
        //Add while conditional, that resets the lap counting system based on laps
        ticksCounter++;
        lapCooldownManager();
        if(!getValidBlock()){
            return;
        }
        blockLapCounter();
    }

    public static lapCounter getInstance(){
        if(laps == null){
            laps = new lapCounter();
        }
        return laps;
    }
}