package org.refabricators.totemexpansion.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import org.refabricators.totemexpansion.TotemExpansion;

public record SyncPlayerDataS2C(boolean usedRecallTotem, int recallDirection) implements CustomPayload {
    public static final CustomPayload.Id<SyncPlayerDataS2C> ID = new CustomPayload.Id<>(TotemExpansion.id("sync_player_data"));
    public static final PacketCodec<RegistryByteBuf, SyncPlayerDataS2C> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, SyncPlayerDataS2C::usedRecallTotem, PacketCodecs.INTEGER, SyncPlayerDataS2C::recallDirection, SyncPlayerDataS2C::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
