package org.refabricators.totemexpansion.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;

import java.util.*;
import java.util.stream.Collectors;

public class StateSaverAndLoader extends PersistentState {
    public List<Integer> activeTimeTotems = new ArrayList<>();
    public HashMap<UUID, PlayerData> players = new HashMap<>();

    PersistentStateType<StateSaverAndLoader> type = new PersistentStateType<>()

    private static Type<StateSaverAndLoader> type = new Type<>(
            StateSaverAndLoader::new,
            StateSaverAndLoader::createFromNbt,
            null
    );

    // writeNbt stores the data, converting from ram to persistent
    // createFromNbt loads data, converting from persistent to ram

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putIntArray("timeTotems", activeTimeTotems.stream().mapToInt(Integer::intValue).toArray());

        NbtCompound playersNbt = new NbtCompound();
        players.forEach((uuid, playerData) -> {
            NbtCompound playerNbt = new NbtCompound();
            playerNbt.putBoolean("usedRecallTotem", playerData.usedRecallTotem.get());
            playerNbt.putInt("recallDirection", playerData.recallDirection.get());

            playersNbt.put(uuid.toString(), playerNbt);
        });
        nbt.put("players", playersNbt);

        return nbt;
    }

    public static StateSaverAndLoader createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        StateSaverAndLoader state = new StateSaverAndLoader();
        state.activeTimeTotems = Arrays.stream(tag.getIntArray("timeTotems").get()).boxed().collect(Collectors.toList());

        NbtCompound playersNbt = new NbtCompound();
        playersNbt.getKeys().forEach(key -> {
            PlayerData playerData = new PlayerData();
            playerData.usedRecallTotem = playersNbt.getCompound(key).get().getBoolean("usedRecallTotem");
            playerData.recallDirection = playersNbt.getCompound(key).get().getInt("recallDirection");
//            playerData.usedRecallTotem = playersNbt.getCompound(key).getBoolean("usedRecallTotem");
//            playerData.recallDirection = playersNbt.getCompound(key).getInt("recallDirection");

            state.players.put(UUID.fromString(key), playerData);
        });

        return state;
    }

    public static PlayerData getPlayerState(LivingEntity player) {
        StateSaverAndLoader serverState = getServerState(player.getServer());
        return serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());
    }

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        StateSaverAndLoader state = persistentStateManager.getOrCreate(type);
        state.markDirty();

        return state;
    }
}