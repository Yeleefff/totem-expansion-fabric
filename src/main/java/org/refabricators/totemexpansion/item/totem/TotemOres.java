package org.refabricators.totemexpansion.item.totem;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.refabricators.totemexpansion.item.ModItems;
import org.refabricators.totemexpansion.item.TotemBase;
import org.refabricators.totemexpansion.mixin.TotemUseInvoker;

import java.util.List;

import static org.refabricators.totemexpansion.TotemExpansion.id;

public class TotemOres extends TotemBase {
    public TotemOres() {
        super(new Item.Settings().component(DataComponentTypes.DEATH_PROTECTION, ModItems.TOTEM_ORES_COMPONENT)
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, id("totem_ores"))));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.totemexpansion.totem_ores.description").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ((TotemUseInvoker) user).useTotem(world.getDamageSources().generic());
        return super.use(world, user, hand);
    }

    @Override
    public void addDamageTypes() {
    }
}