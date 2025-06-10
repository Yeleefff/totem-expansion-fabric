package org.refabricators.totemexpansion.item.totem;

import net.minecraft.component.type.TooltipDisplayComponent;
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
import org.refabricators.totemexpansion.util.StateSaverAndLoader;

import java.util.List;
import java.util.function.Consumer;

public class TotemTime extends TotemBase {
    public TotemTime(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        textConsumer.accept(Text.translatable("tooltip.totemexpansion.totem_time.description").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ((TotemUseInvoker) user).useTotem(world.getDamageSources().generic());

        if (!world.isClient) {
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(world.getServer());
            serverState.activeTimeTotems.add(0);
        }

        return super.use(world, user, hand);
    }

    @Override
    public void addDamageTypes() {}

    @Override
    public void addEffects() {}
}
