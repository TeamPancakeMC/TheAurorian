package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class TAKeyMappings {

    private static final String CATEGORY = "key.categories." + AurorianMod.MOD_ID;

    public static final KeyMapping ARMOR_SET = new KeyMapping(
            "key.theaurorian.armor_set",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_UP, CATEGORY);

}