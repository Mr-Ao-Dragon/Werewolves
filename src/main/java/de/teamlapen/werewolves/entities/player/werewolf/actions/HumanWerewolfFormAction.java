package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModSkills;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class HumanWerewolfFormAction extends WerewolfFormAction {

    public HumanWerewolfFormAction() {
        super(WerewolfForm.HUMAN);
        attributes.add(new Modifier(Attributes.ARMOR, UUID.fromString("9fe8cf0f-e3d1-47f6-ba69-db13920640e1"), UUID.fromString("b5468e4e-c742-4d75-b568-d34411e1874b"), 0.7, "human_form_armor", () -> WerewolvesConfig.BALANCE.SKILLS.human_form_armor.get() * 0.8, WerewolvesConfig.BALANCE.SKILLS.human_form_armor, ModSkills.RESISTANCE, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.ARMOR_TOUGHNESS, UUID.fromString("ae1e52d0-5982-4657-8260-345460e6e02d"), UUID.fromString("6065ab5f-417c-455e-93e3-008b9a9db424"), 0.7, "human_form_armor_toughness", WerewolvesConfig.BALANCE.SKILLS.human_form_armor_toughness, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.MOVEMENT_SPEED, UUID.fromString("f30e65e0-69b0-430c-ae94-8086a7870e63"), UUID.fromString("26ecafa2-bd32-4850-893b-6ac09f1361a1"), 0.5, "human_form_speed_amount", () -> WerewolvesConfig.BALANCE.SKILLS.human_form_speed_amount.get() * 0.8, WerewolvesConfig.BALANCE.SKILLS.human_form_speed_amount, ModSkills.SPEED, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.human_form_enabled.get();
    }

    @Override
    public int getCooldown(IWerewolfPlayer werewolfPlayer) {
        return WerewolvesConfig.BALANCE.SKILLS.human_form_cooldown.get() * 20;
    }

    @Override
    public boolean consumesWerewolfTime() {
        return false;
    }
}
