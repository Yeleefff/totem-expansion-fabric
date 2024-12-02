package org.refabricators.totemexpansion.item.totem;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.refabricators.totemexpansion.item.ModItems;
import org.refabricators.totemexpansion.item.TotemBase;
import org.refabricators.totemexpansion.mixin.TotemUseInvoker;
import org.refabricators.totemexpansion.network.SyncPlayerDataS2C;
import org.refabricators.totemexpansion.util.PlayerData;
import org.refabricators.totemexpansion.util.StateSaverAndLoader;

import java.util.List;

import static org.refabricators.totemexpansion.TotemExpansion.id;

public class TotemRecall extends TotemBase {
    public TotemRecall() {
        super(new Item.Settings().component(DataComponentTypes.DEATH_PROTECTION, ModItems.TOTEM_RECALL_COMPONENT)
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, id("totem_recall"))));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.totemexpansion.totem_recall.description").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, type);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
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
}
