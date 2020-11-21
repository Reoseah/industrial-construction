package reoseah.velvet.blocks.entities;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import alexiil.mc.lib.attributes.item.ItemExtractable;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Direction;
import reoseah.velvet.Velvet;
import reoseah.velvet.blocks.ExtractorBlock;
import reoseah.velvet.blocks.state.OptionalDirection;

public class ExtractorBlockEntity extends ConduitBlockEntity {
    private int transferCooldown;

    public ExtractorBlockEntity() {
        super(Velvet.BlockEntityTypes.EXTRACTOR);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isClient) {
            this.transferCooldown--;
            if (this.transferCooldown <= 0) {
                OptionalDirection optionalDirection = this.getCachedState().get(ExtractorBlock.DIRECTION);
                if (!this.world.isReceivingRedstonePower(this.pos) && optionalDirection != OptionalDirection.NONE) {
                    ItemExtractable extractable = ItemAttributes.EXTRACTABLE.getFromNeighbour(this, optionalDirection.direction);
                    ItemStack stack = extractable.attemptAnyExtraction(8, Simulation.ACTION);
                    if (!stack.isEmpty()) {
                        this.doInsert(stack, optionalDirection.direction);
                        this.transferCooldown = 8;
                        this.markDirty();
                    }
                }
            }
        }
    }

    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.transferCooldown = tag.getInt("TransferCooldown");
    }

    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putInt("TransferCooldown", this.transferCooldown);
        return tag;
    }

    @Override
    protected boolean canSendItems(Direction direction) {
        return super.canSendItems(direction) && direction != this.getCachedState().get(ExtractorBlock.DIRECTION).direction;
    }
}
