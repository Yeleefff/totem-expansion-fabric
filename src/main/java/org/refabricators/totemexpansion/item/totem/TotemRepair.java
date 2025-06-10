package org.refabricators.totemexpansion.item.totem;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.refabricators.totemexpansion.item.TotemBase;

import java.util.List;
import java.util.function.Consumer;

public class TotemRepair extends TotemBase {
    public TotemRepair(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        textConsumer.accept(Text.translatable("tooltip.totemexpansion.totem_repair.description").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }

    @Override
    public void addDamageTypes() {
    }

    @Override
    public void addEffects() {
    }
}
