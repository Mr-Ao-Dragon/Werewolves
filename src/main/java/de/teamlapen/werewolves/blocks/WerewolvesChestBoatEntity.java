package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.items.IWerewolvesBoat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WerewolvesChestBoatEntity extends ChestBoat implements IWerewolvesBoat {

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(WerewolvesChestBoatEntity.class, EntityDataSerializers.INT);

    public WerewolvesChestBoatEntity(@NotNull EntityType<? extends Boat> type, @NotNull Level level) {
        super(type, level);
    }

    public WerewolvesChestBoatEntity(@NotNull Level level, double x, double y, double z) {
        super(ModEntities.CHEST_BOAT.get(), level);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    /**
     * @deprecated use {@link #setType(de.teamlapen.werewolves.items.IWerewolvesBoat.BoatType)}
     */
    @Deprecated
    @Override
    public void setVariant(@NotNull Type type) {
    }

    /**
     * @deprecated use {@link #getBType()}
     */
    @NotNull
    @Deprecated
    @Override
    public Type getVariant() {
        return Type.OAK;
    }

    @Override
    @NotNull
    public IWerewolvesBoat.BoatType getBType() {
        return IWerewolvesBoat.BoatType.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    public void setType(@NotNull IWerewolvesBoat.BoatType type) {
        this.entityData.set(DATA_ID_TYPE, type.ordinal());
    }

    @NotNull
    @Override
    public Item getDropItem() {
        return switch (this.getBType()) {
            case JACARANDA -> ModItems.JACARANDA_CHEST_BOAT.get();
            case MAGIC -> ModItems.MAGIC_CHEST_BOAT.get();
        };
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
        tag.putString("Type", this.getBType().getName());
        this.addChestVehicleSaveData(tag);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
        if (tag.contains("Type", 8)) {
            this.setType(IWerewolvesBoat.BoatType.byName(tag.getString("Type")));
        }
        this.readChestVehicleSaveData(tag);
    }

    @NotNull
    @Override
    protected Component getTypeName() {
        return EntityType.CHEST_BOAT.getDescription();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE, BoatType.JACARANDA.ordinal());
    }
}
