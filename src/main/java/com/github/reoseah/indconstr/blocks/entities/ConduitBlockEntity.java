package com.github.reoseah.indconstr.blocks.entities;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.github.reoseah.indconstr.IndConstr;
import com.github.reoseah.indconstr.blocks.AbstractConduitBlock;
import com.google.common.collect.ImmutableList;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import alexiil.mc.lib.attributes.item.ItemInsertable;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ConduitBlockEntity extends BlockEntity implements Tickable {
    public static final double SPEED = 0.05;

    public final List<TravellingItem> items = new ArrayList<>();

    protected ConduitBlockEntity(BlockEntityType<?> type) {
        super(type);
    }

    public static ConduitBlockEntity createTransparent() {
        return new ConduitBlockEntity(IndConstr.BlockEntityTypes.CONDUIT);
    }

    public static ConduitBlockEntity createOpaque() {
        return new ConduitBlockEntity(IndConstr.BlockEntityTypes.OPAQUE_CONDUIT);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.items.clear();
        ListTag list = tag.getList("Items", NbtType.COMPOUND);
        long time = this.world != null ? this.world.getTime() : 0;
        for (int i = 0; i < list.size(); i++) {
            CompoundTag tag2 = list.getCompound(i);
            TravellingItem item = new TravellingItem(tag2, time);
            if (!item.stack.isEmpty()) {
                this.items.add(item);
            }
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        ListTag list = new ListTag();
        long time = this.world != null ? this.world.getTime() : 0;
        for (TravellingItem item : this.items) {
            list.add(item.toTag(time));
        }
        tag.put("Items", list);

        return tag;
    }

    @Override
    public void tick() {
        long time = this.world.getTime();

        List<TravellingItem> updated = new ArrayList<>();
        for (Iterator<TravellingItem> it = this.items.iterator(); it.hasNext();) {
            TravellingItem item = it.next();
            if (item.timeFinish > time) {
                continue;
            }
            if (this.world.isClient) {
                it.remove();
            } else {
                if (item.to == null) {
                    EnumSet<Direction> directions = EnumSet.allOf(Direction.class);
                    directions.remove(item.from);
                    for (Direction direction : Direction.values()) {
                        if (!this.canSendItems(direction)) {
                            directions.remove(direction);
                        }
                    }
                    List<Direction> destinations = new ArrayList<>(directions);
                    if (destinations.isEmpty()) {
                        destinations.add(item.from);
                    }
                    item.to = destinations.get(this.world.random.nextInt(destinations.size()));
                    item.timeStart = time;
                    item.timeFinish = time + (long) Math.ceil(0.5 / SPEED);
                    updated.add(item);
                } else {
                    ItemInsertable insertable = ItemAttributes.INSERTABLE.getFromNeighbour(this, item.to);
                    ItemStack excess = insertable.attemptInsertion(item.stack, Simulation.ACTION);
                    if (excess.isEmpty()) {
                        it.remove();
                    } else {
                        item.stack = excess;
                        item.from = item.to;
                        item.to = null;
                        item.timeStart = time;
                        item.timeFinish = time + (long) Math.ceil(0.5 / SPEED);
                        updated.add(item);
                    }
                }
            }
        }
        this.markDirty();
        if (!this.world.isClient) {
            this.sendToClient(updated);
        }
    }

    protected boolean canSendItems(Direction direction) {
        return this.isConnected(direction);
    }

    public void sendToClient(List<TravellingItem> list) {
        if (this.getType() != IndConstr.BlockEntityTypes.OPAQUE_CONDUIT) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeBlockPos(this.pos);
            buf.writeInt(list.size());
            for (TravellingItem item : list) {
                buf.writeItemStack(item.stack);
                buf.writeLong(item.timeStart);
                buf.writeLong(item.timeFinish);
                buf.writeByte(item.from.getId());
                if (item.to != null) {
                    buf.writeBoolean(true);
                    buf.writeByte(item.to.getId());
                } else {
                    buf.writeBoolean(false);
                }
            }

            PlayerStream.around(this.world, this.pos, 40).forEach(player -> ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, new Identifier("indconstr:conduit"), buf));
        }
    }

    public void doInsert(ItemStack stack, Direction from) {
        long time = this.world.getTime();
        TravellingItem item = new TravellingItem(stack, from, time, time + (long) Math.ceil(0.5 / SPEED));
        this.items.add(item);
        this.markDirty();
        if (!this.world.isClient) {
            this.sendToClient(ImmutableList.of(item));
        }
    }

    protected boolean isConnected(Direction direction) {
        return this.getCachedState().get(AbstractConduitBlock.getConnectionProperty(direction));
    }

    public static class TravellingItem implements Comparable<TravellingItem> {
        public ItemStack stack;
        public Direction from;
        public @Nullable Direction to;
        public long timeStart, timeFinish;

        public TravellingItem(ItemStack stack, Direction from, long timeStart, long timeFinish) {
            this.stack = stack;
            this.from = from;
            this.timeStart = timeStart;
            this.timeFinish = timeFinish;
        }

        public TravellingItem(CompoundTag tag, long time) {
            this.stack = ItemStack.fromTag(tag.getCompound("Stack"));
            this.from = Direction.byId(tag.getByte("From"));
            this.to = tag.contains("To", NbtType.BYTE) ? Direction.byId(tag.getByte("To")) : null;
            this.timeStart = tag.getLong("Start") + time;
            this.timeFinish = tag.getLong("Finish") + time;
        }

        public CompoundTag toTag(long time) {
            CompoundTag tag = new CompoundTag();
            tag.put("Stack", this.stack.toTag(new CompoundTag()));
            tag.putByte("From", (byte) this.from.getId());
            if (this.to != null) {
                tag.putByte("To", (byte) this.to.getId());
            }
            tag.putLong("Start", this.timeStart - time);
            tag.putLong("Finish", this.timeFinish - time);
            return tag;
        }

        @Override
        public int compareTo(TravellingItem o) {
            return Long.compare(this.timeFinish, o.timeFinish);
        }

        public Vec3d interpolatePosition(long time, float tickDelta, boolean straight) {
            Vec3d center = new Vec3d(0.5, 0.5, 0.5);
            float interp = (time - this.timeStart + tickDelta) / (this.timeFinish - this.timeStart);
            if (this.to != null) {
                interp = MathHelper.clamp(interp, 0, 1.25F);
                Vec3d destination = center.add(Vec3d.of(this.to.getVector()).multiply(0.5));
                return center.multiply(1 - interp).add(destination.multiply(interp));
            } else {
                interp = MathHelper.clamp(interp, 0, straight ? 2F : 1.05F);
                Vec3d source = center.add(Vec3d.of(this.from.getVector()).multiply(0.5));
                return source.multiply(1 - interp).add(center.multiply(interp));
            }
        }
    }

}
