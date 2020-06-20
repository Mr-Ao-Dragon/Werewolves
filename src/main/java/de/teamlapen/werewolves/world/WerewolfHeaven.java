package de.teamlapen.werewolves.world;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.world.IFactionBiome;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.core.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class WerewolfHeaven extends Biome implements IFactionBiome {

    public WerewolfHeaven() {
        super(new Builder().surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG).category(Category.FOREST).depth(0.2f).scale(0.5f).waterColor(0x4CCCFF).waterFogColor(0x4CCCFF).precipitation(RainType.NONE).parent(null).downfall(0.0f).temperature(0.3f));

        DefaultBiomeFeatures.addCarvers(this);

        DefaultBiomeFeatures.addGrass(this);
        ModBiomeFeatures.addJacarandaTree(this);
        ModBiomeFeatures.addMagicTree(this);
        ModBiomeFeatures.addBigTree(this);

        DefaultBiomeFeatures.addSedimentDisks(this);
        DefaultBiomeFeatures.func_222283_Y(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addBerryBushes(this);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public int getFoliageColor(@Nonnull BlockPos pos) {
        return 0x70E0B5;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public int getGrassColor(@Nonnull BlockPos pos) {
        return 0x69CFDB;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public int getSkyColorByTemp(float currentTemperature) {
        return 0x66DBFF;
    }

    @Override
    public IFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }
}
