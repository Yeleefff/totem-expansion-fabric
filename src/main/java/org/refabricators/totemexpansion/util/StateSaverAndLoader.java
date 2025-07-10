package org.refabricators.totemexpansion.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class StateSaverAndLoader extends PersistentState {
    public List<Integer> activeTimeTotems;
    public Map<String, PlayerData> players; //Map<String<UUID>, PlayerData>

    public static final Codec<StateSaverAndLoader> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.list(Codec.INT).fieldOf("activeTimeTotems").forGetter(StateSaverAndLoader::getActiveTimeTotems),
            Codec.unboundedMap(Codec.STRING, PlayerData.CODEC).fieldOf("players").forGetter(StateSaverAndLoader::getPlayers)
    ).apply(builder, StateSaverAndLoader::new));
    private static final PersistentStateType<StateSaverAndLoader> type = new PersistentStateType<>("type", StateSaverAndLoader::new, CODEC, DataFixTypes.PLAYER);

    public StateSaverAndLoader() {
        this.activeTimeTotems = new ArrayList<>();
        this.players = new HashMap<>();
    }

    public StateSaverAndLoader(List<Integer> activeTimeTotems, Map<String, PlayerData> players) {
        this.activeTimeTotems = activeTimeTotems;
        this.players = players;
    }

//    @Override
//    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
//        nbt.putIntArray("timeTotems", activeTimeTotems.stream().mapToInt(Integer::intValue).toArray());
//
//        NbtCompound playersNbt = new NbtCompound();
//        players.forEach((uuid, playerData) -> {
//            NbtCompound playerNbt = new NbtCompound();
//            playerNbt.putBoolean("usedRecallTotem", playerData.usedRecallTotem);
//            playerNbt.putInt("recallDirection", playerData.recallDirection);
//
//            playersNbt.put(uuid, playerNbt);
//        });
//        nbt.put("players", playersNbt);
//
//        return nbt;
//    }
//
//    public static StateSaverAndLoader createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
//        StateSaverAndLoader state = new StateSaverAndLoader();
//        state.activeTimeTotems = Arrays.stream(tag.getIntArray("timeTotems").get()).boxed().collect(Collectors.toList());
//
//        NbtCompound playersNbt = new NbtCompound();
//        playersNbt.getKeys().forEach(key -> {
//            PlayerData playerData = new PlayerData();
//            playerData.usedRecallTotem = playersNbt.getCompound(key).get().getBoolean("usedRecallTotem").get();
//            playerData.recallDirection = playersNbt.getCompound(key).get().getInt("recallDirection").get();
//
//            state.players.put(key, playerData);
//        });
//
//        return state;
//    }

    public static PlayerData getPlayerState(LivingEntity player) {
        StateSaverAndLoader serverState = getServerState(player.getServer());
        return serverState.players.computeIfAbsent(player.getUuid().toString(), uuid -> new PlayerData());
    }

    public static StateSaverAndLoader getServerState(@NotNull MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        StateSaverAndLoader state = persistentStateManager.get(type);
        state.markDirty();

        return state;
    }

    public Map<String, PlayerData> getPlayers() {
        return this.players;
    }

    public List<Integer> getActiveTimeTotems() {
        return this.activeTimeTotems;
    }
}