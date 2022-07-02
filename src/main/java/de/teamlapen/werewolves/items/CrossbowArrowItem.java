package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.api.items.IEntityCrossbowArrow;
import de.teamlapen.vampirism.api.items.IVampirismCrossbowArrow;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.entity.CrossbowArrowEntity;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CrossbowArrowItem extends Item implements IVampirismCrossbowArrow<CrossbowArrowEntity> {

    private final ArrowType type;

    public CrossbowArrowItem(ArrowType type) {
        super(new Item.Properties().tab(WUtils.creativeTab));
        this.type = type;
    }

    @Override
    public CrossbowArrowEntity createEntity(ItemStack stack, Level world, Player player, double heightOffset, double centerOffset, boolean rightHand) {
        CrossbowArrowEntity entity = CrossbowArrowEntity.createWithShooter(world, player, heightOffset, centerOffset, rightHand, stack);
        entity.setBaseDamage(this.type.baseDamage * VampirismConfig.BALANCE.crossbowDamageMult.get());
        return entity;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        if (this.type.hasToolTip) {
            tooltip.add(new TranslatableComponent(this.getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
        }
    }

    public ArrowType getType() {
        return type;
    }

    @Override
    public boolean isCanBeInfinite() {
        return type.canBeInfinit;
    }

    @Override
    public void onHitBlock(ItemStack arrow, BlockPos blockPos, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {
        this.type.onHitBlock(arrow, blockPos, arrowEntity, shootingEntity);
    }

    @Override
    public void onHitEntity(ItemStack arrow, LivingEntity entity, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {
        this.type.onHitEntity(arrow, entity, arrowEntity, shootingEntity);
    }

    public static class ArrowType {
        public final String name;
        public final int baseDamage;
        public final int color;
        public final boolean canBeInfinit;
        public final boolean hasToolTip;

        public ArrowType(String name, int baseDamage, int color, boolean canBeInfinit, boolean hasToolTip) {
            this.name = name;
            this.baseDamage = baseDamage;
            this.color = color;
            this.canBeInfinit = canBeInfinit;
            this.hasToolTip = hasToolTip;
        }

        private void onHitBlock(ItemStack arrow, BlockPos blockPos, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {

        }

        public void onHitEntity(ItemStack arrow, LivingEntity entity, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {

        }

    }

}
