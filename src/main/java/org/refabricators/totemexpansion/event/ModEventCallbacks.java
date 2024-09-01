package org.refabricators.totemexpansion.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.refabricators.totemexpansion.util.PlayerData;
import org.refabricators.totemexpansion.util.StateSaverAndLoader;

import static org.refabricators.totemexpansion.TotemExpansion.id;

public class ModEventCallbacks {
    private static int timeIncrement = 20;

    public static void registerEventCallbacks() {
        ServerTickEvents.START_WORLD_TICK.register((world) -> {
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(world.getServer());

            for (int i = 0; i < serverState.activeTimeTotems.size(); i++) {
                world.setTimeOfDay(world.getTimeOfDay() + timeIncrement);

                ServerPlayerEntity player = world.getRandomAlivePlayer();
                if (player != null) player.networkHandler.sendPacket(new WorldTimeUpdateS2CPacket(world.getTime(), world.getTimeOfDay(), world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)));
                serverState.activeTimeTotems.set(i, serverState.activeTimeTotems.get(i) + 1);

                if (serverState.activeTimeTotems.get(i) >= (int) (10000/timeIncrement)) {
                    serverState.activeTimeTotems.remove(i);
                    i--;
                }
            }
        });

        CustomTotemUsedCallback.EVENT.register((entity, stack, source) -> {});
    }
}
