package com.github.reoseah.icon.enchantments;

import com.github.reoseah.icon.ICon;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class ParsimonyEnchantment extends Enchantment {
    public ParsimonyEnchantment() {
        super(Enchantment.Rarity.COMMON, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() == ICon.Items.PAINT_SCRAPER;
    }
}
