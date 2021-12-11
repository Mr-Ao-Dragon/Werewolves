package de.teamlapen.werewolves.effects;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.player.vampire.VampirePlayer;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class BleedingEffect extends WerewolvesEffect {

    public BleedingEffect() {
        super("bleeding", EffectType.HARMFUL, 0x740000);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int j = 20 >> amplifier;
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.isInvertedHealAndHarm()) {
            entityLivingBaseIn.removeEffect(ModEffects.bleeding);
            return;
        }

        if (entityLivingBaseIn.getHealth() > 1.0F) {
            entityLivingBaseIn.hurt(DamageSource.MAGIC, 0.3F);
            if (entityLivingBaseIn.getRandom().nextInt(4) == 0) {
                if (Helper.isVampire(entityLivingBaseIn)) {
                    if (entityLivingBaseIn instanceof PlayerEntity) {
                        VampirePlayer.getOpt(((PlayerEntity) entityLivingBaseIn)).map(vampire -> vampire.useBlood(2, true));
                    } else if (entityLivingBaseIn instanceof IVampire) {
                        ((IVampire) entityLivingBaseIn).useBlood(1, true);
                    }
                } else if (entityLivingBaseIn instanceof CreatureEntity) {
                    VampirismAPI.getExtendedCreatureVampirism((CreatureEntity) entityLivingBaseIn).ifPresent(creature -> creature.setBlood(creature.getBlood() - 1));
                }
            }
        }
    }
}
