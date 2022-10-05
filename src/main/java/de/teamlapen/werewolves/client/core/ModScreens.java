package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.gui.StoneAltarScreen;
import de.teamlapen.werewolves.core.ModContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModScreens {

    public static void registerScreensUnsafe() {
        ScreenManager.register(ModContainer.STONE_ALTAR_CONTAINER.get(), StoneAltarScreen::new);
    }
}
