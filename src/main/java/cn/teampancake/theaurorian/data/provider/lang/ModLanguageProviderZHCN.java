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
        add(ModEnchantments.LIGHTNING_RESISTANCE.get().getDescriptionId() + ".desc", "降低“雷电”魔咒造成的额外伤害，同时完全抵消被雷击中的伤害");
        add(ModEnchantments.LIGHTNING_DAMAGE.get().getDescriptionId() + ".desc", "根据对方穿戴的护甲数量造成额外伤害");
    }

}