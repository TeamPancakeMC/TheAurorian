package cn.teampancake.theaurorian.data.provider.lang;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.enchantment.ModEnchantments;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProviderENUS extends LanguageProvider {

    public ModLanguageProviderENUS(PackOutput output) {
        super(output, AurorianMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
         add(ModEnchantments.LIGHTNING_RESISTANCE.get(), "Lightning Resistance");
         add(ModEnchantments.LIGHTNING_DAMAGE.get(), "Lightning");
    }

}