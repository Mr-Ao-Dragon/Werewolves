package de.teamlapen.werewolves.entities.player.werewolf.skill;

import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;

public class DiggerSkill extends SimpleWerewolfSkill {
    public DiggerSkill() {
        this.defaultDescWithFormRequirement(ModSkills.BEAST_FORM::get, ModSkills.SURVIVAL_FORM::get, ModSkills.HUMAN_FORM::get);
    }

    @Override
    protected void onEnabled(IWerewolfPlayer player) {
        super.onEnabled(player);
        ((WerewolfPlayer) player).getSpecialAttributes().increaseDiggerLevel();
        ((WerewolfPlayer) player).checkToolDamage(player.asEntity().getMainHandItem(), player.asEntity().getMainHandItem(), true);
    }

    @Override
    protected void onDisabled(IWerewolfPlayer player) {
        super.onDisabled(player);
        ((WerewolfPlayer) player).getSpecialAttributes().decreaseDiggerLevel();
        ((WerewolfPlayer) player).checkToolDamage(player.asEntity().getMainHandItem(), player.asEntity().getMainHandItem(), true);
    }
}
