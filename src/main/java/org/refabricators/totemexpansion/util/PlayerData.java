package org.refabricators.totemexpansion.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class PlayerData {
    public boolean usedRecallTotem = false;
    public int recallDirection = 1;

    public static final Codec<PlayerData> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.BOOL.fieldOf("usedRecallTotem").forGetter(PlayerData::getIfUsedRecallTotem),
            Codec.INT.fieldOf("recallDirection").forGetter(PlayerData::getRecallDirection)
    ).apply(builder, PlayerData::new));

    public PlayerData() {

    }

    public PlayerData(boolean usedRecallTotem, int recallDirection) {
        this.usedRecallTotem = usedRecallTotem;
        this.recallDirection = recallDirection;
    }

    public boolean getIfUsedRecallTotem() {
        return this.usedRecallTotem;
    }

    public int getRecallDirection() {
        return this.recallDirection;
    }
}
