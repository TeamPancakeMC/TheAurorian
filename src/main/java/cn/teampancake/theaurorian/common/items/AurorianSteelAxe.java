package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.util.AurorianSteelHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class AurorianSteelAxe extends AxeItem {
    public AurorianSteelAxe(Tier tier, Properties properties) {
        //TODO ItemRegistry.Materials.AURORIANSTEEL
        super(tier, 10.0F, -3.2F, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable Level levelIn, List<Component> tooltip, TooltipFlag flagIn) {
        AurorianSteelHelper.getAurorianSteelInfo(stack, levelIn, tooltip);
    }
}
