package cn.teampancake.theaurorian.api;

import net.minecraft.world.item.ItemStack;

public interface ISpecialty {

    default void doSpecialty(ItemStack stack) {}

}