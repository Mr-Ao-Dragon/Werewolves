package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.network.InputEventPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

public class ModKeys {
    private static final String CATEGORY = "keys.werewolves.category";
    private static final String LEAP_KEY = "keys.werewolves.leap";
    private static final String BITE_KEY = "keys.werewolves.bite";

    private static final KeyBinding LEAP = new KeyBinding(LEAP_KEY, KeyConflictContext.IN_GAME, KeyModifier.CONTROL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_SPACE, CATEGORY);
    private static final KeyBinding BITE = new KeyBinding(BITE_KEY, KeyConflictContext.IN_GAME, KeyModifier.NONE, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY);

    public static void register(ClientEventHandler clientEventHandler) {
        MinecraftForge.EVENT_BUS.register(new ModKeys(clientEventHandler));
        ClientRegistry.registerKeyBinding(LEAP);
        ClientRegistry.registerKeyBinding(BITE);
    }

    private final ClientEventHandler clientEventHandler;

    public ModKeys(ClientEventHandler clientEventHandler) {
        this.clientEventHandler = clientEventHandler;
    }

    @SubscribeEvent
    public void handleInputEvent(InputEvent event) {
        Optional<KeyBinding> keyOpt = getPressedKeyBinding();
        keyOpt.ifPresent(key -> {
            PlayerEntity player = Minecraft.getInstance().player;
            LazyOptional<WerewolfPlayer> werewolfOpt = WerewolfPlayer.getOptEx(Minecraft.getInstance().player);
            if (key == LEAP) {
                werewolfOpt.filter(w -> !w.getActionHandler().isActionOnCooldown(ModActions.LEAP.get()) && w.getForm().isTransformed()).ifPresent(w -> {
                    WerewolvesMod.dispatcher.sendToServer(new InputEventPacket(InputEventPacket.LEAP, ""));
                    WerewolfPlayer.get(player).getActionHandler().toggleAction(ModActions.LEAP.get());
                });
            } else if (key == BITE) {
                werewolfOpt.ifPresent(werewolf -> {
                    RayTraceResult mouseOver = Minecraft.getInstance().hitResult;
                    Entity entity = mouseOver instanceof EntityRayTraceResult ? ((EntityRayTraceResult) mouseOver).getEntity() : null;
                    if (entity instanceof LivingEntity && werewolf.canBite() && werewolf.canBiteEntity(((LivingEntity) entity))) {
                        WerewolvesMod.dispatcher.sendToServer(new InputEventPacket(InputEventPacket.BITE, "" + ((EntityRayTraceResult) mouseOver).getEntity().getId()));
                        clientEventHandler.onZoomPressed();
                    }
                });
            }
        });
    }

    public Optional<KeyBinding> getPressedKeyBinding() {
        if (LEAP.consumeClick()) {
            return Optional.of(LEAP);
        } else if (BITE.consumeClick()) {
            return Optional.of(BITE);
        }
        return Optional.empty();
    }
}
