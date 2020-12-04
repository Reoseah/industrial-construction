package reoseah.velvet.blocks.entities;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.item.ItemAttributes;
import alexiil.mc.lib.attributes.item.ItemExtractable;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeverBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Direction;
import reoseah.velvet.Velvet;
import reoseah.velvet.blocks.ExtractorBlock;

public class ExtractorBlockEntity extends ConduitBlockEntity {
    protected int transferCooldown;

    public ExtractorBlockEntity() {
        super(Velvet.BlockEntityTypes.EXTRACTOR);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isClient) {
            this.transferCooldown--;
            if (this.transferCooldown <= 0) {
                Direction direction = this.getCachedState().get(ExtractorBlock.DIRECTION);
                if (!this.world.isReceivingRedstonePower(this.pos)) {
                    ItemExtractable extractable = ItemAttributes.EXTRACTABLE.getFromNeighbour(this, direction);
                    ItemStack stack = extractable.attemptAnyExtraction(8, Simulation.ACTION);
                    if (!stack.isEmpty()) {
                        this.doInsert(stack, direction);
                        this.transferCooldown = 8;
                        this.markDirty();
                    }
                }
            }
        }
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.transferCooldown = tag.getInt("TransferCooldown");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putInt("TransferCooldown", this.transferCooldown);
        return tag;
    }

    @Override
    protected boolean canSendItems(Direction direction) {
        return super.canSendItems(direction) && direction != this.getCachedState().get(ExtractorBlock.DIRECTION) && !(this.world.getBlockState(this.getPos().offset(direction)).getBlock() instanceof LeverBlock);
    }
}
