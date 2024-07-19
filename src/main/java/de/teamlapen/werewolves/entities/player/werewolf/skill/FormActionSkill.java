package de.teamlapen.werewolves.entities.player.werewolf.skill;

import de.teamlapen.vampirism.api.entity.factions.ISkillTree;
import de.teamlapen.vampirism.entity.player.skills.ActionSkill;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.actions.WerewolfFormAction;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class FormActionSkill extends ActionSkill<IWerewolfPlayer> {

    public FormActionSkill(Supplier<WerewolfFormAction> action, ResourceKey<ISkillTree> skillTree, int skillPointCost) {
        super(action, skillTree, skillPointCost, true);
    }

    @Override
    protected void onDisabled(IWerewolfPlayer player) {
        super.onDisabled(player);
    }
}
