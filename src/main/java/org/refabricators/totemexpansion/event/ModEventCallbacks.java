package org.refabricators.totemexpansion.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.refabricators.totemexpansion.util.PlayerData;
import org.refabricators.totemexpansion.util.StateSaverAndLoader;

import static org.refabricators.totemexpansion.TotemExpansion.id;

public class ModEventCallbacks {
    public static final Identifier INITIAL_SYNC = id("initial_sync");
    private static int timeIncrement = 12;

    public static void registerEventCallbacks() {
//        ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
//            PlayerData playerData = StateSaverAndLoader.getPlayerState(handler.getPlayer());
//            PacketByteBuf data = PacketByteBufs.create();
//            data.writeBoolean(playerData.usedRecallTotem);
//            server.execute(() -> {
//                ServerPlayNetworking.send();
//            });
//        }));

        ServerTickEvents.START_WORLD_TICK.register((world) -> {
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(world.getServer());

            for (int i = 0; i < serverState.activeTimeTotems.size(); i++) {
                world.setTimeOfDay(world.getTimeOfDay() + timeIncrement);
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
