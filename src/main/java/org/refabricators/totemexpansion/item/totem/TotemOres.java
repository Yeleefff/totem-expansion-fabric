package org.refabricators.totemexpansion.item.totem;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.refabricators.totemexpansion.TotemExpansion;
import org.refabricators.totemexpansion.item.TotemBase;
import org.refabricators.totemexpansion.mixin.TotemUseInvoker;

import java.util.List;
import java.util.function.Consumer;

public class TotemOres extends TotemBase {
    public TotemOres(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        textConsumer.accept(Text.translatable("tooltip.totemexpansion.totem_ores.description").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ((TotemUseInvoker) user).useTotem(world.getDamageSources().generic());
        return super.use(world, user, hand);
    }

    @Override
    public void addDamageTypes() {
    }

    @Override
    public void addEffects()
    {
        effects.add(new StatusEffectInstance(TotemExpansion.SPELUNKING_EFFECT, 900));
    }
}