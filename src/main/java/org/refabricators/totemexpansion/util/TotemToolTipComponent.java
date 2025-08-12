package org.refabricators.totemexpansion.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.refabricators.totemexpansion.TotemExpansion;

import java.util.function.Consumer;

public record TotemToolTipComponent(String name) implements TooltipAppender {
    public static final Codec<TotemToolTipComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(TotemToolTipComponent::name)
    ).apply(instance, TotemToolTipComponent::new));

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        textConsumer.accept(Text.translatable(Util.createTranslationKey("tooltip", Identifier.of(TotemExpansion.MOD_ID,this.name + ".description"))));
    }
}
