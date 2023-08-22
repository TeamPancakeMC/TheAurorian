package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.util.AurorianSteelHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class AurorianSteelItemArmor extends ArmorItem {
    public AurorianSteelItemArmor(Type pType, Properties pProperties) {
        super(ItemRegistry.Materials.AURORIANSTEEL_ARMOR, pType, pProperties);
    }

    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable Level levelIn, List<Component> tooltip, TooltipFlag flagIn) {
        AurorianSteelHelper.getAurorianSteelInfo(stack, levelIn, tooltip);
    }

    public static void handleDamageEvent(LivingDamageEvent e) {
        LivingEntity target = e.getEntity();
        if (target == null) {
            return;
        }
        for (ItemStack piece : target.getArmorSlots()) {
            if (piece.getItem() instanceof ArmorItem armorpart) {
                if (armorpart.getMaterial() == ItemRegistry.Materials.AURORIANSTEEL_ARMOR) {
                    AurorianSteelHelper.handleAurorianSteelDurability(piece);
                }
            }
        }
    }
}
