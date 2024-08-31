package org.refabricators.totemexpansion.item.totem;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.refabricators.totemexpansion.TotemExpansion;
import org.refabricators.totemexpansion.item.TotemBase;
import org.refabricators.totemexpansion.mixin.TotemUseInvoker;
import org.refabricators.totemexpansion.network.SyncPlayerDataS2C;
import org.refabricators.totemexpansion.util.PlayerData;
import org.refabricators.totemexpansion.util.StateSaverAndLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TotemRecall extends TotemBase {
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.totemexpansion.totem_recall.description").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, type);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.getDimensionEntry().matchesKey(DimensionTypes.OVERWORLD) || world.getDimensionEntry().matchesKey(DimensionTypes.OVERWORLD_CAVES)) {
            ((TotemUseInvoker) user).useTotem(world.getDamageSources().generic());

            if (!world.isClient) {
                PlayerData playerState = StateSaverAndLoader.getPlayerState(user);

                if (playerState.usedRecallTotem) {
                    playerState.recallDirection = 1;
                } else {
                    playerState.usedRecallTotem = true;
                }

                ServerPlayNetworking.send((ServerPlayerEntity) user, new SyncPlayerDataS2C(playerState.usedRecallTotem, playerState.recallDirection));
            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public void addDamageTypes() {
    }

    @Override
    public void addEffects() {
    }
}
