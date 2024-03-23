package cn.teampancake.theaurorian.api;

import net.minecraft.world.item.ItemStack;

public interface ISpecialty {
    
    //功能
    default void doSpecialty(ItemStack stack) {}

}