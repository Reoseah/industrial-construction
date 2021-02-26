package com.github.reoseah.iconstruct.items;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;

public class DyeScrapItem extends Item {
    private static final Map<DyeColor, DyeScrapItem> INSTANCES = Maps.newEnumMap(DyeColor.class);

    private final DyeColor color;

    public DyeScrapItem(DyeColor color, Item.Settings settings) {
        super(settings);
        this.color = color;
        INSTANCES.put(color, this);
    }

    public DyeColor getColor() {
        return this.color;
    }

    public static DyeScrapItem byColor(DyeColor color) {
        return INSTANCES.get(color);
    }
}
