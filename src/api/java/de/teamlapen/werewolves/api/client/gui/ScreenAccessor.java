package de.teamlapen.werewolves.api.client.gui;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;

@SuppressWarnings("UnusedReturnValue")
public interface ScreenAccessor {

    Font getFont();

    <T extends AbstractWidget & GuiEventListener  & NarratableEntry> T invokeAddRenderableWidget_werewolves(T button);

    void invokeRemoveWidget(GuiEventListener listener);

}
