package de.teamlapen.werewolves.config;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.util.RegUtil;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ServerConfig {

    public final ForgeConfigSpec.ConfigValue<List<? extends String>> customMeatItems;
    public final ForgeConfigSpec.ConfigValue<List<? extends String>> customRawMeatItems;
    public final ForgeConfigSpec.BooleanValue disableToothInfection;
    public final ForgeConfigSpec.ConfigValue<List<? extends String>> werewolfFormFreeFormBiomes;

    ServerConfig(ForgeConfigSpec.@NotNull Builder builder) {
        werewolfFormFreeFormBiomes = builder.comment("Additional biomes the player can have unlimited time in werewolf form. Use biome ids e.g. [\"minecraft:mesa\", \"minecraft:plains\"]").defineList("werewolfFormFreeFormBiomes", Collections.emptyList(), string -> string instanceof String && UtilLib.isValidResourceLocation(((String) string)));

        builder.push("meat");
        this.customMeatItems = builder.comment("Add edible items that should be considered as meat.", "Format: [\"minecraft:cooked_beef\", \"minecraft:cooked_mutton\"]").defineList("customMeatItems", Collections.emptyList(), string -> string instanceof String && UtilLib.isValidResourceLocation(((String) string)));
        this.customRawMeatItems = builder.comment("Add edible items that should be considered as raw meat. Items should also be included in 'customMeatItems'", "Format: [\"minecraft:beef\", \"minecraft:rabbit\"]").defineList("customRawMeatItems", Collections.emptyList(), string -> string instanceof String && UtilLib.isValidResourceLocation(((String) string)));
        builder.pop();

        builder.push("disable");
        this.disableToothInfection = builder.comment("Disable the ability to infect a player with Lupus Sanguinem by using a werewolf tooth").define("disableToothInfection", false);
        builder.pop();
    }

    public boolean isCustomMeatItems(@NotNull Item item) {
        return this.customMeatItems.get().contains(RegUtil.id(item).toString());
    }

    public boolean isCustomRawMeatItems(@NotNull Item item) {
        return this.customRawMeatItems.get().contains(RegUtil.id(item).toString());
    }
}
