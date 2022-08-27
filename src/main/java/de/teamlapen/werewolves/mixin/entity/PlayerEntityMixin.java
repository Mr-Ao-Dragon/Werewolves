package de.teamlapen.werewolves.mixin.entity;

import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    private PlayerEntityMixin(@NotNull EntityType<? extends LivingEntity> p_i48577_1_, @NotNull Level p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
    }

    @Inject(method = "hurtArmor(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At("HEAD"), cancellable = true)
    private void doNotHurtArmorIfWerewolf(DamageSource p_230294_1_, float p_230294_2_, @NotNull CallbackInfo ci) {
        WerewolfPlayer.getOpt(((Player) (Object) this)).filter(w -> w.getLevel() > 0).filter(w -> w.getForm().isTransformed() && (!w.getForm().isHumanLike() || !w.getSkillHandler().isSkillEnabled(ModSkills.WEAR_ARMOR.get()))).ifPresent(werewolf -> {
            ci.cancel();
        });
    }
}
