package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.blocks.WerewolvesBoatEntity;
import de.teamlapen.werewolves.blocks.WerewolvesChestBoatEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class WerewolvesBoatItem extends Item {
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

    private final IWerewolvesBoat.BoatType type;
    private final boolean hasChest;

    public WerewolvesBoatItem(IWerewolvesBoat.BoatType type, boolean hasChest, @NotNull Properties properties) {
        super(properties);
        this.type = type;
        this.hasChest = hasChest;
    }

    public IWerewolvesBoat.BoatType getType() {
        return type;
    }

    /**
     * from {@link net.minecraft.world.item.BoatItem#use(net.minecraft.world.level.Level, net.minecraft.world.entity.player.Player, net.minecraft.world.InteractionHand)}
     */
    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(@NotNull Level p_77659_1_, @NotNull Player p_77659_2_, @NotNull InteractionHand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
        HitResult hitresult = getPlayerPOVHitResult(p_77659_1_, p_77659_2_, ClipContext.Fluid.ANY);
        if (hitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            Vec3 vec3 = p_77659_2_.getViewVector(1.0F);
            double d0 = 5.0D;
            List<Entity> list = p_77659_1_.getEntities(p_77659_2_, p_77659_2_.getBoundingBox().expandTowards(vec3.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vec3 vec31 = p_77659_2_.getEyePosition();

                for (Entity entity : list) {
                    AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (aabb.contains(vec31)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if (hitresult.getType() == HitResult.Type.BLOCK) {
                IWerewolvesBoat boat = getBoat(p_77659_1_, hitresult);
                Entity botEntity = (Entity) boat;
                boat.setType(this.type);
                botEntity.setYRot(p_77659_2_.getYRot());
                if (!p_77659_1_.noCollision(botEntity, botEntity.getBoundingBox())) {
                    return InteractionResultHolder.fail(itemstack);
                } else {
                    if (!p_77659_1_.isClientSide) {
                        p_77659_1_.addFreshEntity(botEntity);
                        p_77659_1_.gameEvent(p_77659_2_, GameEvent.ENTITY_PLACE, hitresult.getLocation());
                        if (!p_77659_2_.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }

                    p_77659_2_.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, p_77659_1_.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }

    private @NotNull IWerewolvesBoat getBoat(Level level, @NotNull HitResult hitResult) {
        return hasChest ? new WerewolvesChestBoatEntity(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z) : new WerewolvesBoatEntity(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
    }
}
