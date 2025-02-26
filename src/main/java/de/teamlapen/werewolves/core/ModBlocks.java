package de.teamlapen.werewolves.core;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.blocks.TotemTopBlock;
import de.teamlapen.werewolves.blocks.LeavesBlock;
import de.teamlapen.werewolves.blocks.*;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.tree.WTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(REFERENCE.MODID);

    public static final DeferredBlock<DropExperienceBlock> SILVER_ORE = registerWithItem("silver_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Block.Properties.of().mapColor(MapColor.STONE).strength(3.0F, 5.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_SILVER_ORE = registerWithItem("deepslate_silver_ore", () -> new DropExperienceBlock(ConstantInt.of(0), Block.Properties.of().mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<WolfsbaneBlock> WOLFSBANE = registerWithItem("wolfsbane", WolfsbaneBlock::new);
    public static final DeferredBlock<Block> SILVER_BLOCK = registerWithItem("silver_block", () -> new Block(Block.Properties.of().mapColor(MapColor.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = registerWithItem("raw_silver_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.RAW_IRON).requiresCorrectToolForDrops().strength(5.0f, 6.0f)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WOLFSBANE = BLOCKS.register("potted_wolfsbane", () ->  {
        var pot = new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WOLFSBANE, Block.Properties.of().noCollission().pushReaction(PushReaction.DESTROY).instabreak().noOcclusion().strength(0f));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(WOLFSBANE.getId(), () -> pot);
        return pot;
    });
    public static final DeferredBlock<TotemTopBlock> TOTEM_TOP_WEREWOLVES_WEREWOLF = BLOCKS.register("totem_top_werewolves_werewolf", () -> new TotemTopBlock(false, REFERENCE.WEREWOLF_PLAYER_KEY));
    public static final DeferredBlock<TotemTopBlock> TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED = BLOCKS.register("totem_top_werewolves_werewolf_crafted", () -> new TotemTopBlock(true, REFERENCE.WEREWOLF_PLAYER_KEY));
    public static final DeferredBlock<StoneAltarBlock> STONE_ALTAR = registerWithItem("stone_altar", StoneAltarBlock::new);
    public static final DeferredBlock<StoneAltarFireBowlBlock> STONE_ALTAR_FIRE_BOWL = registerWithItem("stone_altar_fire_bowl", StoneAltarFireBowlBlock::new);
    public static final DeferredBlock<Block> DAFFODIL = registerWithItem("daffodil", DaffodilBlock::new);
    public static final DeferredBlock<Block> POTTED_DAFFODIL = BLOCKS.register("potted_daffodil", () ->  {
        var pot = new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DAFFODIL, Block.Properties.of().pushReaction(PushReaction.DESTROY).instabreak().noOcclusion());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(DAFFODIL.getId(), () -> pot);
        return pot;
    });
    public static final DeferredBlock<Block> WOLF_BERRY_BUSH = BLOCKS.register("wolf_berry_bush", () -> new WolfBerryBushBlock(Block.Properties.of().isViewBlocking(UtilLib::never).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH)));

    public static final DeferredBlock<LeavesBlock> JACARANDA_LEAVES = registerWithItem("jacaranda_leaves", () -> new LeavesBlock(Block.Properties.of().mapColor(MapColor.COLOR_PINK).isViewBlocking(UtilLib::never).ignitedByLava().strength(0.2F).pushReaction(PushReaction.DESTROY).randomTicks().sound(SoundType.GRASS).noOcclusion()));
    public static final DeferredBlock<LeavesBlock> MAGIC_LEAVES = registerWithItem("magic_leaves", () -> new LeavesBlock(Block.Properties.of().isViewBlocking(UtilLib::never).mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().strength(0.2F).pushReaction(PushReaction.DESTROY).randomTicks().sound(SoundType.GRASS).noOcclusion()));
    public static final DeferredBlock<Block> JACARANDA_PLANKS = registerWithItem("jacaranda_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> MAGIC_PLANKS = registerWithItem("magic_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<LogBlock> STRIPPED_JACARANDA_LOG = registerWithItem("stripped_jacaranda_log", () -> new LogBlock(MapColor.COLOR_BLACK, MapColor.COLOR_PINK));
    public static final DeferredBlock<LogBlock> STRIPPED_MAGIC_LOG = registerWithItem("stripped_magic_log", () -> new LogBlock(MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE));
    public static final DeferredBlock<LogBlock> JACARANDA_LOG = registerWithItem("jacaranda_log", () -> new StrippableLogBlock(MapColor.COLOR_PINK, MapColor.COLOR_PINK, STRIPPED_JACARANDA_LOG));
    public static final DeferredBlock<LogBlock> MAGIC_LOG = registerWithItem("magic_log", () -> new StrippableLogBlock(MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE, STRIPPED_MAGIC_LOG));
    public static final DeferredBlock<SaplingBlock> JACARANDA_SAPLING = registerWithItem("jacaranda_sapling", () -> new SaplingBlock(WTreeGrower.JACARANDA, BlockBehaviour.Properties.of().isViewBlocking(UtilLib::never).ignitedByLava().pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_PINK).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<SaplingBlock> MAGIC_SAPLING = registerWithItem("magic_sapling", () -> new SaplingBlock(WTreeGrower.MAGIC, BlockBehaviour.Properties.of().isViewBlocking(UtilLib::never).ignitedByLava().pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<DoorBlock> JACARANDA_DOOR = registerWithItem("jacaranda_door", () -> new DoorBlock(LogBlock.JACARANDA_BLOCK_TYPE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final DeferredBlock<DoorBlock> MAGIC_DOOR = registerWithItem("magic_door", () -> new DoorBlock(LogBlock.MAGIC_BLOCK_TYPE, BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final DeferredBlock<TrapDoorBlock> JACARANDA_TRAPDOOR = registerWithItem("jacaranda_trapdoor", () -> new TrapDoorBlock(WBlockSetTypes.JACARANDA, BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_PINK).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)));
    public static final DeferredBlock<TrapDoorBlock> MAGIC_TRAPDOOR = registerWithItem("magic_trapdoor", () -> new TrapDoorBlock(WBlockSetTypes.MAGIC, BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)));
    public static final DeferredBlock<StairBlock> JACARANDA_STAIRS = registerWithItem("jacaranda_stairs", () -> new StairBlock(() -> JACARANDA_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(JACARANDA_PLANKS.get())));
    public static final DeferredBlock<StairBlock> MAGIC_STAIRS = registerWithItem("magic_stairs", () -> new StairBlock(() -> MAGIC_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(MAGIC_PLANKS.get())));
    public static final DeferredBlock<LogBlock> STRIPPED_JACARANDA_WOOD = registerWithItem("stripped_jacaranda_wood", () -> new LogBlock(MapColor.COLOR_PINK, MapColor.COLOR_PINK));
    public static final DeferredBlock<LogBlock> STRIPPED_MAGIC_WOOD = registerWithItem("stripped_magic_wood", () -> new LogBlock(MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE));
    public static final DeferredBlock<LogBlock> JACARANDA_WOOD = registerWithItem("jacaranda_wood", () -> new StrippableLogBlock(MapColor.COLOR_PINK, MapColor.COLOR_PINK, STRIPPED_JACARANDA_WOOD));
    public static final DeferredBlock<LogBlock> MAGIC_WOOD = registerWithItem("magic_wood", () -> new StrippableLogBlock(MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE, STRIPPED_MAGIC_WOOD));
    public static final DeferredBlock<StandingSignBlock> JACARANDA_SIGN = BLOCKS.register("jacaranda_sign", () -> new StandingSignBlock(LogBlock.JACARANDA, BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_PINK).noCollission().strength(1.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<StandingSignBlock> MAGIC_SIGN = BLOCKS.register("magic_sign", () -> new StandingSignBlock(LogBlock.MAGIC, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().noCollission().strength(1.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<WallSignBlock> JACARANDA_WALL_SIGN = BLOCKS.register("jacaranda_wall_sign", () -> new WallSignBlock(LogBlock.JACARANDA, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(JACARANDA_SIGN)));
    public static final DeferredBlock<WallSignBlock> MAGIC_WALL_SIGN = BLOCKS.register("magic_wall_sign", () -> new WallSignBlock(LogBlock.MAGIC, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(MAGIC_SIGN)));
    public static final DeferredBlock<PressurePlateBlock> JACARANDA_PRESSURE_PLATE = registerWithItem("jacaranda_pressure_plate", () -> new PressurePlateBlock(LogBlock.JACARANDA_BLOCK_TYPE, BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_PINK).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final DeferredBlock<PressurePlateBlock> MAGIC_PRESSURE_PLATE = registerWithItem("magic_pressure_plate", () -> new PressurePlateBlock(LogBlock.MAGIC_BLOCK_TYPE, BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final DeferredBlock<ButtonBlock> JACARANDA_BUTTON = registerWithItem("jacaranda_button", () -> new ButtonBlock(LogBlock.JACARANDA_BLOCK_TYPE, 30, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).isViewBlocking(UtilLib::never).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final DeferredBlock<ButtonBlock> MAGIC_BUTTON = registerWithItem("magic_button", () -> new ButtonBlock(LogBlock.MAGIC_BLOCK_TYPE, 30, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).isViewBlocking(UtilLib::never).noCollission().noOcclusion().isValidSpawn(ModBlocks::never).strength(0.5F).sound(SoundType.WOOD)));
    public static final DeferredBlock<SlabBlock> JACARANDA_SLAB = registerWithItem("jacaranda_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<SlabBlock> MAGIC_SLAB = registerWithItem("magic_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<FenceGateBlock> JACARANDA_FENCE_GATE = registerWithItem("jacaranda_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final DeferredBlock<FenceGateBlock> MAGIC_FENCE_GATE = registerWithItem("magic_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final DeferredBlock<FenceBlock> JACARANDA_FENCE = registerWithItem("jacaranda_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<FenceBlock> MAGIC_FENCE = registerWithItem("magic_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER = registerWithItem("wolfsbane_diffuser_normal", () -> new WolfsbaneDiffuserBlock(WolfsbaneDiffuserBlock.Type.NORMAL));
    public static final DeferredBlock<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER_LONG = registerWithItem("wolfsbane_diffuser_long", () -> new WolfsbaneDiffuserBlock(WolfsbaneDiffuserBlock.Type.LONG));
    public static final DeferredBlock<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER_IMPROVED = registerWithItem("wolfsbane_diffuser_improved", () -> new WolfsbaneDiffuserBlock(WolfsbaneDiffuserBlock.Type.IMPROVED));


    public static class V {
        public static final DeferredHolder<Block, Block> MED_CHAIR = DeferredBlock.create(ResourceKey.create(Registries.BLOCK, new ResourceLocation("vampirism", "med_chair")));

        private static void init() {

        }
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    private static <T extends Block> DeferredBlock<T> registerWithItem(String name, Supplier<T> supplier) {
        return registerWithItem(name, supplier, new Item.Properties());
    }

    private static <T extends Block> DeferredBlock<T> registerWithItem(String name, Supplier<T> supplier, Item.Properties properties) {
        DeferredBlock<T> block = BLOCKS.register(name, supplier);
        ModItems.register(name, () -> new BlockItem(block.get(), properties));
        return block;
    }

    public static Set<Block> getAllBlocks() {
        return BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toSet());
    }

    private static boolean never(BlockState pState, BlockGetter pLevel, BlockPos pPos, EntityType<?> pValue) {
        return false;
    }
}
