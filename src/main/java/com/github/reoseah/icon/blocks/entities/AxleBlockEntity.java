package com.github.reoseah.icon.blocks.entities;

import com.github.reoseah.icon.ICon;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;

public class AxleBlockEntity extends BlockEntity implements Tickable {
    public float angle;
    public boolean isRotating = true;

    public AxleBlockEntity() {
        super(ICon.BlockEntityTypes.AXLE);
    }

    @Override
    public void tick() {
        this.angle += 3.6F / 180F * Math.PI;
        if (this.angle > 2 * Math.PI) {
            this.angle -= 2 * Math.PI;
        }
    }

}
