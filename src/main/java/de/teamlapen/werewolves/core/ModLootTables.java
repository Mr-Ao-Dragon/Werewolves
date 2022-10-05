package de.teamlapen.werewolves.core;

import com.google.common.collect.Maps;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.loot.MobLootModifier;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class ModLootTables {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, REFERENCE.MODID);
    private static final Map<ResourceLocation, ResourceLocation> INJECTION_TABLES = Maps.newHashMap();

    // entities
    public static final ResourceLocation villager = entity(EntityType.VILLAGER);
    public static final ResourceLocation skeleton = entity(EntityType.SKELETON);

    // chests
    public static final ResourceLocation abandoned_mineshaft = chest("abandoned_mineshaft");
    public static final ResourceLocation jungle_temple = chest("jungle_temple");
    public static final ResourceLocation stronghold_corridor = chest("stronghold_corridor");
    public static final ResourceLocation desert_pyramid = chest("desert_pyramid");
    public static final ResourceLocation stronghold_library = chest("stronghold_library");

    public static final RegistryObject<GlobalLootModifierSerializer<MobLootModifier>> MOB_MODIFIER = LOOT_MODIFIER_SERIALIZERS.register("mob_modifier", MobLootModifier.ModLootModifierSerializer::new);

    static void registerLootModifier(IEventBus bus) {
        LOOT_MODIFIER_SERIALIZERS.register(bus);
    }
    static ResourceLocation entity(EntityType<?> type) {
        ResourceLocation loc = type.getDefaultLootTable();
        ResourceLocation newLoc = new ResourceLocation(REFERENCE.MODID, "inject/entity/" + loc.getPath());
        INJECTION_TABLES.put(loc, newLoc);
        return newLoc;
    }

    static ResourceLocation chest(String chest) {
        ResourceLocation loc = new ResourceLocation("chests/" + chest);
        ResourceLocation newLoc = new ResourceLocation(REFERENCE.MODID, "inject/chest/" + chest);
        INJECTION_TABLES.put(loc, newLoc);
        return newLoc;
    }

    @SubscribeEvent
    public static void onLootLoad(LootTableLoadEvent event) {
        if (INJECTION_TABLES.containsKey(event.getName())) {
            try {
                event.getTable().addPool(getInjectPool(event.getName()));
            } catch (NullPointerException ignored) {

            }
        }
    }

    private static LootPool getInjectPool(ResourceLocation loc) {
        TableLootEntry.lootTableReference(INJECTION_TABLES.get(loc)).setWeight(1);
        return LootPool.lootPool().name("werewolves_inject_pool").bonusRolls(0,1).setRolls(new RandomValueRange(1)).add(TableLootEntry.lootTableReference(INJECTION_TABLES.get(loc)).setWeight(1)).build();
    }

}
