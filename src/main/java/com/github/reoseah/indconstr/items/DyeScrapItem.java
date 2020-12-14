package com.github.reoseah.indconstr.items;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;

public class DyeScrapItem extends Item {
    private static final Map<DyeColor, DyeScrapItem> DYES = Maps.newEnumMap(DyeColor.class);
    private final DyeColor color;

    public DyeScrapItem(DyeColor color, Item.Settings settings) {
        super(settings);
        this.color = color;
        DYES.put(color, this);
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof SheepEntity) {
            SheepEntity sheepEntity = (SheepEntity) entity;
            if (sheepEntity.isAlive() && !sheepEntity.isSheared() && sheepEntity.getColor() != this.color) {
                if (!user.world.isClient) {
                    sheepEntity.setColor(this.color);
                    stack.decrement(1);
                }

                return ActionResult.success(user.world.isClient);
            }
        }

        return ActionResult.PASS;
    }

    public DyeColor getColor() {
        return this.color;
    }

    public static DyeScrapItem byColor(DyeColor color) {
        return DYES.get(color);
    }
}
