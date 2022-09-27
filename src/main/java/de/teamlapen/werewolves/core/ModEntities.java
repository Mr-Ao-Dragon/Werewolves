package de.teamlapen.werewolves.core;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.teamlapen.vampirism.entity.hunter.BasicHunterEntity;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.entities.AggressiveWolfEntity;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.entities.werewolf.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Set;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModEntities {

    private static final Set<EntityType<?>> ALL_ENTITIES = Sets.newHashSet();

    public static final EntityType<BasicWerewolfEntity.Beast> werewolf_beast;
    public static final EntityType<BasicWerewolfEntity.Survivalist> werewolf_survivalist;
    public static final EntityType<HumanWerewolfEntity> human_werewolf;
    public static final EntityType<WerewolfTaskMasterEntity> task_master_werewolf = getNull();
    public static final EntityType<AggressiveWolfEntity> wolf = getNull();
    public static final EntityType<WerewolfMinionEntity> werewolf_minion = getNull();
    public static final EntityType<WerewolfAlphaEntity> alpha_werewolf;

    @ObjectHolder(de.teamlapen.vampirism.REFERENCE.MODID)
    public static class V {
        public static final EntityType<BasicHunterEntity> hunter = getNull();
        public static final EntityType<BasicHunterEntity.IMob> hunter_imob = getNull();
    }

    static void registerEntities(IForgeRegistry<EntityType<?>> registry) {
        registry.register(werewolf_beast);
        registry.register(werewolf_survivalist);
        registry.register(human_werewolf);
        registry.register(alpha_werewolf);
        registry.register(prepareEntityType("wolf", EntityType.Builder.of(AggressiveWolfEntity::new, MobCategory.AMBIENT).sized(0.6F, 0.85F), false));
        registry.register(prepareEntityType("task_master_werewolf", EntityType.Builder.of(WerewolfTaskMasterEntity::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.6f, 1.95f), true));
        registry.register(prepareEntityType("werewolf_minion", EntityType.Builder.of(WerewolfMinionEntity::new, MobCategory.CREATURE).sized(0.6f, 1.95f), false));
        WerewolfMinionEntity.registerMinionData();
    }

    static void registerSpawns() {
        SpawnPlacements.register(werewolf_beast, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf);
        SpawnPlacements.register(werewolf_survivalist, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf);
        SpawnPlacements.register(human_werewolf, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HumanWerewolfEntity::spawnPredicateHumanWerewolf);
        SpawnPlacements.register(wolf, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, spawnReason, blockPos, random) -> true);
        SpawnPlacements.register(alpha_werewolf, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WerewolfAlphaEntity::spawnPredicateAlpha);
    }

    private static <T extends Entity> EntityType<T> prepareEntityType(String id, EntityType.Builder<T> builder, boolean spawnable) {
        EntityType.Builder<T> type = builder.setTrackingRange(10).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true);
        if (!spawnable)
            type.noSummon();
        EntityType<T> entry = type.build(REFERENCE.MODID + ":" + id);
        entry.setRegistryName(REFERENCE.MODID, id);
        ALL_ENTITIES.add(entry);
        return entry;
    }

    static void onRegisterEntityTypeAttributes(EntityAttributeCreationEvent event) {
        event.put(human_werewolf, HumanWerewolfEntity.getAttributeBuilder().build());
        event.put(werewolf_beast, BasicWerewolfEntity.getAttributeBuilder().build());
        event.put(werewolf_survivalist, BasicWerewolfEntity.getAttributeBuilder().build());
        event.put(wolf, AggressiveWolfEntity.createAttributes().build());
        event.put(task_master_werewolf, WerewolfTaskMasterEntity.getAttributeBuilder().build());
        event.put(werewolf_minion, WerewolfMinionEntity.getAttributeBuilder().build());
        event.put(alpha_werewolf, WerewolfAlphaEntity.getAttributeBuilder().build());
    }

    static void onModifyEntityTypeAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.bite_damage);
        event.add(EntityType.PLAYER, ModAttributes.time_regain);
    }

    //needed for world gen
    static {
        werewolf_beast = prepareEntityType("werewolf_beast", EntityType.Builder.of(BasicWerewolfEntity.Beast::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 2f), true);
        werewolf_survivalist = prepareEntityType("werewolf_survivalist", EntityType.Builder.of(BasicWerewolfEntity.Survivalist::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 1f), true);
        human_werewolf = prepareEntityType("human_werewolf", EntityType.Builder.of(HumanWerewolfEntity::new, MobCategory.CREATURE).sized(0.6f, 1.9f), true);
        alpha_werewolf = prepareEntityType("alpha_werewolf", EntityType.Builder.of(WerewolfAlphaEntity::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 2f), true);
    }

    public static Set<EntityType<?>> getAllEntities() {
        return ImmutableSet.copyOf(ALL_ENTITIES);
    }
}
