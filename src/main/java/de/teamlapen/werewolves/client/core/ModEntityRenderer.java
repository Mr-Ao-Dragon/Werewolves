package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.*;
import de.teamlapen.werewolves.core.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.apache.logging.log4j.LogManager;

@OnlyIn(Dist.CLIENT)
public class ModEntityRenderer {

    public static WerewolfPlayerRenderer render;

    public static void registerEntityRenderer() {
        render = new WerewolfPlayerRenderer(Minecraft.getInstance().getEntityRenderDispatcher());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.werewolf_beast.get(), WerewolfBeastRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.werewolf_survivalist.get(), WerewolfSurvivalistRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.human_werewolf.get(), HumanWerewolfRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.wolf.get(), WolfRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.task_master_werewolf.get(), WerewolfTaskMasterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.werewolf_minion.get(), safeFactory(WerewolfMinionRenderer::new));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.alpha_werewolf.get(), WerewolfAlphaRenderer::new);
    }

    private static <T extends Entity> IRenderFactory<? super T> safeFactory(IRenderFactory<? super T> f) {
        return (IRenderFactory<T>) manager -> {
            try {
                return f.createRenderFor(manager);
            } catch (Exception e) {
                LogManager.getLogger().error("Failed to instantiate entity renderer", e);
                System.exit(0);
                throw e;
            }
        };
    }
}
