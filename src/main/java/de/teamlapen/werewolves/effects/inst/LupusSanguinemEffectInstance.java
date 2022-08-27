package de.teamlapen.werewolves.effects.inst;

import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class LupusSanguinemEffectInstance extends MobEffectInstance {
    public LupusSanguinemEffectInstance(int durationIn) {
        super(ModEffects.LUPUS_SANGUINEM.get(), durationIn, 0, false, true);
        this.setNoCounter(true);
    }

    @Override
    public boolean update(@NotNull MobEffectInstance other) {
        return false;
    }

    @Override
    public boolean tick(@NotNull LivingEntity entityIn, @NotNull Runnable runnable) {
        if (this.getDuration() % 10 == 0 && entityIn instanceof Player) {
            if (!Helper.canBecomeWerewolf((Player) entityIn)) {
                return false;
            }
        }
        return super.tick(entityIn, runnable);
    }
}
