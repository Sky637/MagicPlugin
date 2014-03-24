package com.elmakers.mine.bukkit.traders;

import net.dandielo.citizens.traders_v3.utils.items.Attribute;

import org.bukkit.inventory.ItemStack;

import com.elmakers.mine.bukkit.plugins.magic.wand.Wand;

@Attribute(name="Wand", key="wand", priority = 5)
public class WandItem extends NBTItem {
	
	public WandItem(String key) {
		super(key);
	}

	@Override
	protected boolean isItem(ItemStack itemStack) {
		return Wand.isWand(itemStack);
	}
}
