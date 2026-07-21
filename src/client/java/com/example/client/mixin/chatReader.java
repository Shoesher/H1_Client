package com.example.client.mixin;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.tools.obfuscation.interfaces.IMessagerEx;
import org.w3c.dom.Text;

@Mixin(ChatScreen.class)

public class chatReader {

    @Inject(method = "s", at = @At("RETURN"), cancellable = true)

    public void onGameMessage(IMessagerEx.MessageType type, Text message, CallbackInfo ci) {

    }

}
