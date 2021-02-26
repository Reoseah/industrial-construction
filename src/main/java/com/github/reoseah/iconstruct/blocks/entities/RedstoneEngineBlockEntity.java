package com.github.reoseah.iconstruct.blocks.entities;

import com.github.reoseah.iconstruct.IConstruct;
import com.github.reoseah.iconstruct.blocks.AxleBlock;
import com.github.reoseah.iconstruct.blocks.RedstoneEngineBlock;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class RedstoneEngineBlockEntity extends BlockEntity implements Tickable {
    public float angle;
    public float startAngle;
    public long startTime;

    public boolean isRotating = true;

    public RedstoneEngineBlockEntity() {
        super(IConstruct.ICBlockEntities.REDSTONE_ENGINE);
    }

    @Override
    public void tick() {
        if (this.world.isReceivingRedstonePower(this.pos)) {
            if (!this.isRotating) {
                this.startAngle = this.angle;
                this.startTime = this.world.getTime();
                this.isRotating = true;
                for (int i = 0; i < 16; i++) {
                    Direction facing = this.getCachedState().get(RedstoneEngineBlock.FACING).getOpposite();
                    BlockPos axlepos = this.pos.offset(facing, i);
                    BlockEntity be = this.world.getBlockEntity(axlepos);
                    if (be instanceof AxleBlockEntity && be.getCachedState().get(AxleBlock.AXIS) == facing.getAxis()) {
                        AxleBlockEntity axle = (AxleBlockEntity) be;
                        axle.startAngle = this.angle;
                        axle.startTime = this.world.getTime();
                        axle.isRotating = true;
                    } else {
                        break;
                    }
                }
            } else {
                this.angle = this.startAngle + (float) ((this.world.getTime() - this.startTime) * 3.6F / 180F * Math.PI);
            }
        } else if (this.isRotating) {
            this.isRotating = false;
            for (int i = 0; i < 16; i++) {
                Direction facing = this.getCachedState().get(RedstoneEngineBlock.FACING).getOpposite();
                BlockPos axlepos = this.pos.offset(facing, i);
                BlockEntity be = this.world.getBlockEntity(axlepos);
                if (be instanceof AxleBlockEntity && be.getCachedState().get(AxleBlock.AXIS) == facing.getAxis()) {
                    AxleBlockEntity axle = (AxleBlockEntity) be;
                    axle.isRotating = false;
                } else {
                    break;
                }
            }
        }
    }
}
