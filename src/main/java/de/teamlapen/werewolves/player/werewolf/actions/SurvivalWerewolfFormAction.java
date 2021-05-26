package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.WerewolfForm;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;

import java.util.UUID;

public class SurvivalWerewolfFormAction extends WerewolfFormAction {

    public SurvivalWerewolfFormAction() {
        super(WerewolfForm.SURVIVALIST);
        attributes.add(new Modifier(Attributes.ARMOR, UUID.fromString("d45bf864-acab-4fb9-9440-0319483e7fdb"), UUID.fromString("2eff230c-b652-4d61-961b-cc992d9eec8a"), 0.7, "survival_form_armor", () -> WerewolvesConfig.BALANCE.SKILLS.survival_form_armor.get() * 0.8, WerewolvesConfig.BALANCE.SKILLS.survival_form_armor::get, WerewolfSkills.resistance, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.ARMOR_TOUGHNESS, UUID.fromString("ad6a329c-5ca0-4b7b-8bd5-f3f17f3fba00"), UUID.fromString("724e45dd-7454-4c6c-96e0-b485a010e5c0"), 0.7, "survival_form_armor_toughness", WerewolvesConfig.BALANCE.SKILLS.survival_form_armor_toughness::get, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.MOVEMENT_SPEED, UUID.fromString("429ac45a-05e7-4102-b506-e1f1a3a6aca9"), UUID.fromString("0d027b0c-b87b-484c-b4ca-36bae0a2f9b9"), 0.6, "survival_form_armor_speed", () -> WerewolvesConfig.BALANCE.SKILLS.survival_form_speed_amount.get() * 0.8, WerewolvesConfig.BALANCE.SKILLS.survival_form_speed_amount::get, WerewolfSkills.speed, AttributeModifier.Operation.MULTIPLY_TOTAL));
        attributes.add(new Modifier(Attributes.ATTACK_DAMAGE, UUID.fromString("4e36859f-fadd-43cb-8e0d-722b7ab2cd4c"), UUID.fromString("a62d12ee-20e1-4169-a802-1eab2d0cc471"), 0.5, "survival_form_attack_damage", () -> WerewolvesConfig.BALANCE.SKILLS.survival_form_attack_damage.get() * 0.5, WerewolvesConfig.BALANCE.SKILLS.survival_form_attack_damage::get, WerewolfSkills.damage, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolfPlayer) {
        if (super.activate(werewolfPlayer)) {
            if (werewolfPlayer.getSkillHandler().isSkillEnabled(WerewolfSkills.climber)) {
                werewolfPlayer.getRepresentingPlayer().stepHeight = 1.0f;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer werewolfPlayer) {
        super.onActivatedClient(werewolfPlayer);
        if (werewolfPlayer.getSkillHandler().isSkillEnabled(WerewolfSkills.climber)) {
            werewolfPlayer.getRepresentingPlayer().stepHeight = 1.0f;
//            WerewolvesMod.proxy.toggleStepHeight(true);
        }
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolfPlayer) {
        super.onDeactivated(werewolfPlayer);
        werewolfPlayer.getRepresentingPlayer().stepHeight = 0.6f;
//        WerewolvesMod.proxy.toggleStepHeight(false);
    }

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.survival_form_enabled.get();
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.survival_form_cooldown.get() * 20;
    }

}