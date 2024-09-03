package org.refabricators.totemexpansion.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.sound.GuardianAttackSoundInstance;
import net.minecraft.client.sound.SnifferDigSoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import org.refabricators.totemexpansion.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin extends ClientCommonNetworkHandler implements ClientPlayPacketListener, TickablePacketListener {
   @Shadow
   private ClientWorld world;

    public ClientPlayNetworkHandlerMixin(MinecraftClient client, ClientConnection connection, ClientConnectionState connectionState) {
        super(client, connection, connectionState);
    }

    @Override
    public void onEntityStatus(EntityStatusS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = packet.getEntity(this.world);

        if (entity != null) {
            switch (packet.getStatus()) {
                case 63 -> {
                    this.client.getSoundManager().play(new SnifferDigSoundInstance((SnifferEntity) entity));
                }
                case 21 -> {
                    this.client.getSoundManager().play(new GuardianAttackSoundInstance((GuardianEntity) entity));
                }
                case 35, 75, 76, 77, 78, 79, 80, 81, 82-> {
                    int i = 40;
                    this.client.particleManager.addEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
                    this.world.playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0f, 1.0f, false);
                    if (entity != this.client.player) break;

                    switch (packet.getStatus()) {
                        case 35 -> {this.client.gameRenderer.showFloatingItem(Items.TOTEM_OF_UNDYING.getDefaultStack());}
                        case 75 -> {this.client.gameRenderer.showFloatingItem(ModItems.TOTEM_FALLING.getDefaultStack());}
                        case 76 -> {this.client.gameRenderer.showFloatingItem(ModItems.TOTEM_FIRE.getDefaultStack());}
                        case 77 -> {this.client.gameRenderer.showFloatingItem(ModItems.TOTEM_BREATHING.getDefaultStack());}
                        case 78 -> {this.client.gameRenderer.showFloatingItem(ModItems.TOTEM_EXPLOSION.getDefaultStack());}
                        case 79 -> {this.client.gameRenderer.showFloatingItem(ModItems.TOTEM_ORES.getDefaultStack());}
                        case 80 -> {this.client.gameRenderer.showFloatingItem(ModItems.TOTEM_REPAIR.getDefaultStack());}
                        case 81 -> {this.client.gameRenderer.showFloatingItem(ModItems.TOTEM_TIME.getDefaultStack());}
                        case 82 -> {this.client.gameRenderer.showFloatingItem(ModItems.TOTEM_RECALL.getDefaultStack());}
                    }
                }
                default -> {
                    entity.handleStatus(packet.getStatus());
                }
            }
        }
    }
}
