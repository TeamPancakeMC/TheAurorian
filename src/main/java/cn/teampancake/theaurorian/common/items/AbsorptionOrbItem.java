package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.config.AurorianConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AbsorptionOrbItem{

    public static final DeferredRegister<Item> ITEMS;

    public static final RegistryObject<Item> ABSORPTION_ORB;

    static {
        ITEMS=DeferredRegister.create(ForgeRegistries.ITEMS, AurorianMod.MODID);

        ABSORPTION_ORB = ITEMS.register("absorptionorb", () -> new Item((new Item.Properties())
                .defaultDurability(AurorianConfig.Config_OrbOfAbsorptionDurability.get())
                .rarity(Rarity.EPIC)
        ));
    }



//    @Override
//    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
//        if (entityIn instanceof EntityPlayer) {
//            EntityPlayer p = (EntityPlayer) entityIn;
//            if (p.getHeldItem(EnumHand.OFF_HAND).getItem() == this) {
//                ItemStack offhand = p.getHeldItem(EnumHand.OFF_HAND);
//                ItemStack mainhand = p.getHeldItem(EnumHand.MAIN_HAND);
//                switch (AurorianConfig.Config_OrbOfAbsorptionWhitelistBlacklist) {
//                    case 0:
//                    default:
//                        if (mainhand.isItemStackDamageable() && mainhand.isItemDamaged()) {
//                            if (!p.isCreative()) {
//                                offhand.damageItem(1, p);
//                            }
//                            mainhand.setItemDamage(mainhand.getItemDamage() - 1);
//                            return;
//                        }
//                        break;
//                    case 1:
//                        for (String i : AurorianConfig.Config_OrbOfAbsorptionList) {
//                            if (Item.getByNameOrId(i) == mainhand.getItem()) {
//                                if (mainhand.isItemStackDamageable() && mainhand.isItemDamaged()) {
//                                    if (!p.isCreative()) {
//                                        offhand.damageItem(1, p);
//                                    }
//                                    mainhand.setItemDamage(mainhand.getItemDamage() - 1);
//                                    return;
//                                }
//                            } else if (!i.contains(":")) {
//                                if (i.equals(mainhand.getItem().getRegistryName().getResourceDomain())) {
//                                    if (mainhand.isItemStackDamageable() && mainhand.isItemDamaged()) {
//                                        if (!p.isCreative()) {
//                                            offhand.damageItem(1, p);
//                                        }
//                                        mainhand.setItemDamage(mainhand.getItemDamage() - 1);
//                                        return;
//                                    }
//                                }
//                            }
//                        }
//                        break;
//                    case 2:
//                        for (String i : AurorianConfig.Config_OrbOfAbsorptionList) {
//                            if (Item.getByNameOrId(i) == mainhand.getItem()) {
//                                return;
//                            } else if (!i.contains(":")) {
//                                if (i.equals(mainhand.getItem().getRegistryName().getResourceDomain())) {
//                                    return;
//                                }
//                            }
//                        }
//                        if (mainhand.isItemStackDamageable() && mainhand.isItemDamaged()) {
//                            if (!p.isCreative()) {
//                                offhand.damageItem(1, p);
//                            }
//                            mainhand.setItemDamage(mainhand.getItemDamage() - 1);
//                            return;
//                        }
//                        break;
//                }
//            }
//        }
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//        if (!GuiScreen.isShiftKeyDown()) {
//            tooltip.add(TextFormatting.ITALIC + I18n.format("string.theaurorian.tooltip.shiftinfo") + TextFormatting.RESET);
//        } else {
//            tooltip.add(I18n.format("string.theaurorian.tooltip.absorptionorb"));
//        }
//    }

}
