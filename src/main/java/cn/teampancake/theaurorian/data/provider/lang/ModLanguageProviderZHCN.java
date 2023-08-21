package cn.teampancake.theaurorian.data.provider.lang;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.enchantment.ModEnchantments;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProviderZHCN extends LanguageProvider {

    public ModLanguageProviderZHCN(PackOutput output) {
        super(output, AurorianMod.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(ModEnchantments.LIGHTNING_RESISTANCE.get(), "雷电抵御");
        add(ModEnchantments.LIGHTNING_DAMAGE.get(), "雷电");
    }

}