package de.teamlapen.werewolves.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.core.ModFeatures;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.WerewolfHeavenBiome;
import de.teamlapen.werewolves.world.WerewolvesBiomeFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.List;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;


public class ModBiomes extends de.teamlapen.vampirism.core.ModBiomes {
    @ObjectHolder(REFERENCE.MODID + ":werewolf_heaven")
    public static final Biome werewolf_heaven = getNull();
    public static final RegistryKey<Biome> WEREWOLF_HEAVEN_KEY = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation(REFERENCE.MODID, "werewolf_heaven"));

    static void registerBiomes(IForgeRegistry<Biome> registry) {
        registry.register(WerewolfHeavenBiome.createWerewolfHeavenBiome().setRegistryName(WEREWOLF_HEAVEN_KEY.getLocation()));

        VampirismAPI.worldGenRegistry().removeStructureFromBiomes(ModFeatures.hunter_camp.getRegistryName(), Lists.newArrayList(WEREWOLF_HEAVEN_KEY.getLocation()));
        BiomeDictionary.addTypes(WEREWOLF_HEAVEN_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.HILLS);
    }

    static void addBiomesToGeneratorUnsafe() {
        if (!WerewolvesConfig.COMMON.disableWerewolfHeaven.get()) {
            List<RegistryKey<Biome>> modlist = new ArrayList<>(OverworldBiomeProvider.biomes);
            modlist.add(WEREWOLF_HEAVEN_KEY);
            OverworldBiomeProvider.biomes = ImmutableList.copyOf(modlist);
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(WEREWOLF_HEAVEN_KEY, WerewolvesConfig.COMMON.werewolfHeavenWeight.get()));
        }
    }

    public static void onBiomeLoadingEventAdditions(BiomeLoadingEvent event) {
        if (!WerewolvesConfig.COMMON.disableOreGen.get() && event.getCategory() != Biome.Category.THEEND && event.getCategory() != Biome.Category.NETHER) {
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, WerewolvesBiomeFeatures.silver_ore);
        }
        if (event.getCategory() == Biome.Category.FOREST) {
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, WerewolvesBiomeFeatures.wolfsbane);
        }

        if (event.getCategory() == Biome.Category.TAIGA) {
            event.getSpawns().withSpawner(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnInfo.Spawners(ModEntities.human_werewolf, 3, 1, 1));
            event.getSpawns().withSpawner(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnInfo.Spawners(ModEntities.werewolf_survivalist, 1, 1, 1));
            event.getSpawns().withSpawner(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnInfo.Spawners(ModEntities.werewolf_beast, 1, 1, 1));
        }

    }
}
