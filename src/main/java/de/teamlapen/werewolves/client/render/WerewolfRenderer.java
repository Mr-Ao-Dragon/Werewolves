package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.render.layer.WerewolfEntityFaceOverlayLayer;
import de.teamlapen.werewolves.entities.werewolf.WerewolfBaseEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class WerewolfRenderer<T extends WerewolfBaseEntity> extends MobRenderer<T, WerewolfBaseModel<T>> {

    private final ResourceLocation[] textures;

    public WerewolfRenderer(EntityRendererProvider.Context context, WerewolfBaseModel<T> entityModelIn, float shadowSizeIn, ResourceLocation[] textures) {
        super(context, entityModelIn, shadowSizeIn);
        this.addLayer(new WerewolfEntityFaceOverlayLayer<>(this));
        this.textures = textures;
    }

    public ResourceLocation getWerewolfTexture(int entityId) {
        return this.textures[entityId % textures.length];
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull T entity) {
        return this.getWerewolfTexture(entity.getSkinType());
    }
}
