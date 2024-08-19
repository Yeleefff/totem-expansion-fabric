package org.refabricators.totemexpansion.item.totem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
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

import java.util.List;

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
                for (int i = 0; i < TotemExpansion.activeRecallTotems.size(); i++) {
                    PlayerEntity player = (PlayerEntity) TotemExpansion.activeRecallTotems.get(i).get(1);

                    if (user.getUuidAsString().equals(player.getUuidAsString())) {
                        TotemExpansion.activeRecallTotems.remove(i);
                        break;
                    }
                }

                TotemExpansion.activeRecallTotems.add(List.of(world, user));
                System.out.println("Totem added to recall list");
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
