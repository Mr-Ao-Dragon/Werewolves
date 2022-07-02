package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.blocks.TotemTopBlock;
import de.teamlapen.werewolves.blocks.SaplingBlock;
import de.teamlapen.werewolves.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.blocks.StoneAltarFireBowlBlock;
import de.teamlapen.werewolves.blocks.WolfsbaneBlock;
import de.teamlapen.werewolves.mixin.BlocksAccessor;
import de.teamlapen.werewolves.mixin.FireBlockAccessor;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import de.teamlapen.werewolves.world.tree.JacarandaTree;
import de.teamlapen.werewolves.world.tree.MagicTree;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, REFERENCE.MODID);

    public static final RegistryObject<OreBlock> silver_ore = registerWithItem("silver_ore", () -> new OreBlock(Block.Properties.of(Material.STONE).strength(3.0F, 5.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<OreBlock> deepslate_silver_ore = registerWithItem("deepslate_silver_ore", () -> new OreBlock(Block.Properties.of(Material.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().color(MaterialColor.DEEPSLATE).sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<WolfsbaneBlock> wolfsbane = registerWithItem("wolfsbane", WolfsbaneBlock::new);
    public static final RegistryObject<Block> silver_block = registerWithItem("silver_block", () -> new Block(Block.Properties.of(Material.METAL, MaterialColor.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final RegistryObject<Block> raw_silver_block = registerWithItem("raw_silver_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.RAW_IRON).requiresCorrectToolForDrops().strength(5.0f, 6.0f)));
    public static final RegistryObject<FlowerPotBlock> potted_wolfsbane = BLOCKS.register("potted_wolfsbane", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, wolfsbane, Block.Properties.of(Material.DECORATION).strength(0f)));
    public static final RegistryObject<TotemTopBlock> totem_top_werewolves_werewolf = BLOCKS.register("totem_top_werewolves_werewolf", () -> new TotemTopBlock(false, REFERENCE.WEREWOLF_PLAYER_KEY));
    public static final RegistryObject<TotemTopBlock> totem_top_werewolves_werewolf_crafted = BLOCKS.register("totem_top_werewolves_werewolf_crafted", () -> new TotemTopBlock(true, REFERENCE.WEREWOLF_PLAYER_KEY));
    public static final RegistryObject<net.minecraft.world.level.block.SaplingBlock> jacaranda_sapling = registerWithItem("jacaranda_sapling", () -> new SaplingBlock(new JacarandaTree()));
    public static final RegistryObject<LeavesBlock> jacaranda_leaves = registerWithItem("jacaranda_leaves", () -> new LeavesBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<RotatedPillarBlock> jacaranda_log = registerWithItem("jacaranda_log", () -> {
        RotatedPillarBlock log = BlocksAccessor.createLogBlock_werewolves(MaterialColor.COLOR_BROWN, MaterialColor.COLOR_BROWN);
        ((FireBlockAccessor) Blocks.FIRE).invokeSetFireInfo_werewolves(log, 5, 5);
        return log;
    });
    public static final RegistryObject<net.minecraft.world.level.block.SaplingBlock> magic_sapling = registerWithItem("magic_sapling", () -> new SaplingBlock(new MagicTree()));
    public static final RegistryObject<LeavesBlock> magic_leaves = registerWithItem("magic_leaves", () -> new LeavesBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<RotatedPillarBlock> magic_log = registerWithItem("magic_log", () -> {
        RotatedPillarBlock log = BlocksAccessor.createLogBlock_werewolves(MaterialColor.COLOR_BLUE, MaterialColor.COLOR_BLUE);
        ((FireBlockAccessor) Blocks.FIRE).invokeSetFireInfo_werewolves(log, 5, 5);
        return log;
    });
    public static final RegistryObject<Block> magic_planks = registerWithItem("magic_planks", () -> new Block(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<StoneAltarBlock> stone_altar = registerWithItem("stone_altar", StoneAltarBlock::new);
    public static final RegistryObject<StoneAltarFireBowlBlock> stone_altar_fire_bowl = registerWithItem("stone_altar_fire_bowl", StoneAltarFireBowlBlock::new);

    public static class V {
        public static final RegistryObject<Block> med_chair = RegistryObject.create(new ResourceLocation("vampirism", "med_chair"), ForgeRegistries.BLOCKS);

        private static void init() {

        }
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    private static <T extends Block> RegistryObject<T> registerWithItem(String name, Supplier<T> supplier) {
        return registerWithItem(name, supplier, new Item.Properties().tab(WUtils.creativeTab));
    }

    private static <T extends Block> RegistryObject<T> registerWithItem(String name, Supplier<T> supplier, Item.Properties properties) {
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
        return block;
    }

    public static Set<Block> getAllBlocks() {
        return BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toSet());
    }
}
