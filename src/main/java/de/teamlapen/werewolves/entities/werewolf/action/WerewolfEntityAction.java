package de.teamlapen.werewolves.entities.werewolf.action;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.DefaultEntityAction;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.werewolves.api.WReference;
import org.jetbrains.annotations.NotNull;

public abstract class WerewolfEntityAction extends DefaultEntityAction {

    public WerewolfEntityAction(@NotNull EntityActionTier tier, EntityClassType... param) {
        super(tier, param);
    }

    @Override
    public IPlayableFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }
}
