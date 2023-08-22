package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.utils.AurorianSteelHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class AurorianSteelArmor extends ArmorItem {

    public AurorianSteelArmor(Type type, Properties properties) {
        super(ModArmorMaterials.AURORIAN_STEEL, type, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        AurorianSteelHelper.getAurorianSteelInfo(stack, level, tooltip);
    }

}
