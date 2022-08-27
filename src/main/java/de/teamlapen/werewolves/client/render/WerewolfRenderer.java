package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.render.layer.WerewolfEntityFaceOverlayLayer;
import de.teamlapen.werewolves.entities.werewolf.WerewolfBaseEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class WerewolfRenderer<T extends WerewolfBaseEntity> extends MobRenderer<T, WerewolfBaseModel<T>> {

    private final ResourceLocation[] textures;

    public WerewolfRenderer(EntityRendererProvider.@NotNull Context context, @NotNull WerewolfBaseModel<T> entityModelIn, float shadowSizeIn, ResourceLocation[] textures) {
        super(context, entityModelIn, shadowSizeIn);
        this.addLayer(new WerewolfEntityFaceOverlayLayer<>(this));
        this.textures = textures;
    }

    public ResourceLocation getWerewolfTexture(int entityId) {
        return this.textures[entityId % textures.length];
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull T entity) {
        return this.getWerewolfTexture(entity.getSkinType());
    }
}
