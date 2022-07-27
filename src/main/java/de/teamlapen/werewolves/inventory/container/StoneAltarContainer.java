package de.teamlapen.werewolves.inventory.container;

import de.teamlapen.lib.lib.inventory.InventoryContainer;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModContainer;
import de.teamlapen.werewolves.core.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IWorldPosCallable;

public class StoneAltarContainer extends InventoryContainer {
    public static final SelectorInfo[] SELECTOR_INFOS;


    @Deprecated
    public StoneAltarContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new Inventory(2), IWorldPosCallable.NULL);
    }

    public StoneAltarContainer(int id, PlayerInventory playerInventory, IInventory inventory, IWorldPosCallable worldPos) {
        super(ModContainer.STONE_ALTAR_CONTAINER.get(), id, playerInventory, worldPos, inventory, SELECTOR_INFOS);
        this.addPlayerSlots(playerInventory);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(this.worldPos, playerIn, ModBlocks.STONE_ALTAR.get());
    }

    static {
        SELECTOR_INFOS = new SelectorInfo[]{
                new SelectorInfo(Ingredient.of(ModItems.LIVER.get()), 62, 34),
                new SelectorInfo(Ingredient.of(ModItems.CRACKED_BONE.get()), 98, 34)
        };
    }
}
