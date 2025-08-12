package org.refabricators.totemexpansion.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.refabricators.totemexpansion.TotemExpansion;

import java.util.*;

public class StateSaverAndLoader extends PersistentState {
    public List<Integer> activeTimeTotems;
    public Map<String, PlayerData> players;

    public static Codec<StateSaverAndLoader> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.INT.listOf().fieldOf("activeTimeTotems").forGetter(StateSaverAndLoader::getActiveTimeTotems),
            Codec.unboundedMap(Codec.STRING, PlayerData.CODEC).fieldOf("players").forGetter(StateSaverAndLoader::getPlayers)
    ).apply(builder, StateSaverAndLoader::new));
    private static final PersistentStateType<StateSaverAndLoader> type = new PersistentStateType<>("totem_data_storage", StateSaverAndLoader::new, CODEC, DataFixTypes.PLAYER);

    public StateSaverAndLoader() {
        this.activeTimeTotems = new ArrayList<>();
        this.players = new HashMap<>();
    }

    private StateSaverAndLoader(List<Integer> activeTimeTotems, Map<String, PlayerData> players) {
        this.activeTimeTotems = new ArrayList<>(activeTimeTotems);
        this.players = new HashMap<>(players);
    }

    /**
     *
     * @param player
     * @return the given players data if it exists, or creates a new data object
     */
    public static PlayerData getOrCreatePlayerData(@NotNull LivingEntity player) {
        StateSaverAndLoader serverState = getServerState(player.getServer());
        return serverState.players.computeIfAbsent(player.getUuid().toString(), uuid -> new PlayerData());
    }

    /**
     *
     * @param server
     * @return the persistent state object if it exists, or creates a new one
     */
    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        try {
            return server.getWorld(World.OVERWORLD).getPersistentStateManager().getOrCreate(type);
        } catch (NullPointerException e) {
            TotemExpansion.LOGGER.error("PersistentStateManager is null", e);
            return new StateSaverAndLoader();
        }
    }

    public Map<String, PlayerData> getPlayers() {
        return this.players;
    }

    public List<Integer> getActiveTimeTotems() {
        return this.activeTimeTotems;
    }
}