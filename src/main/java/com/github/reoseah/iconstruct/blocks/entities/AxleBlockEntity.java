package com.github.reoseah.iconstruct.blocks.entities;

import com.github.reoseah.iconstruct.IConstruct;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;

public class AxleBlockEntity extends BlockEntity implements Tickable {
    public float angle;
    public float startAngle;
    public long startTime;

    public boolean isRotating = false;

    public AxleBlockEntity() {
        super(IConstruct.BlockEntityTypes.AXLE);
    }

    @Override
    public void tick() {
        if (this.isRotating) {
            this.angle = this.startAngle + (float) ((this.world.getTime() - this.startTime) * 3.6F / 180F * Math.PI);
        }
    }

}
