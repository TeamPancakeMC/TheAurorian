package cn.teampancake.theaurorian.common.utils;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.*;

public class TAPotionUtils {

    public static final HashSet<Holder<MobEffect>> NEGATIVE_EFFECTS = new HashSet<>();
    public static final HashMap<Holder<MobEffect>, String> EFFECT_FORMULAS = new HashMap<>();
    public static final HashMap<Holder<MobEffect>, String> AMPLIFIER_FORMULAS = new HashMap<>();
    public static final HashMap<Item, String> INGREDIENT_FORMULAS = new HashMap<>();

    private static boolean isBitSet(int data, int bitPosition) {
        return ((data & 1 << bitPosition % 15) != 0);
    }

    private static boolean checkBit(int data, int bitPosition) {
        return ((data & 1 << bitPosition) != 0);
    }

    private static int getPrefixBitValue(int data, int bitPosition) {
        return checkBit(data, bitPosition) ? 1 : 0;
    }

    private static int getInvertedBitValue(int data, int bitPosition) {
        return checkBit(data, bitPosition) ? 0 : 1;
    }

    public static int getPrefixNumber(int damage) {
        return extractBinaryNumber(damage, 14, 9, 7, 3, 2);
    }

    public static int getPotionColor(int damage) {
        int red = (extractBinaryNumber(damage, 2, 14, 11, 8, 5) ^ 0x3) << 3;
        int green = (extractBinaryNumber(damage, 0, 12, 9, 6, 3) ^ 0x6) << 3;
        int blue = (extractBinaryNumber(damage, 13, 10, 4, 1, 7) ^ 0x8) << 3;
        return red << 16 | green << 8 | blue;
    }

    private static int calculateBitOperation(
            boolean invert, boolean multiply, boolean negate,
            int comparison, int bitPosition, int multiplier, int data) {
        int result = 0;
        if (invert) {
            result = getInvertedBitValue(data, bitPosition);
        } else if (comparison != -1) {
            if (comparison == 0 && countSetBits(data) == bitPosition) {
                result = 1;
            } else if (comparison == 1 && countSetBits(data) > bitPosition) {
                result = 1;
            } else if (comparison == 2 && countSetBits(data) < bitPosition) {
                result = 1;
            }
        } else {
            result = getPrefixBitValue(data, bitPosition);
        }

        if (multiply) {
            result *= multiplier;
        }

        if (negate) {
            result *= -1;
        }

        return result;
    }

    private static int countSetBits(int data) {
        int count = 0;
        for (; data > 0; count++) {
            data &= data - 1;
        }

        return count;
    }

    private static int parseEffectFormula(String formula, int start, int end, int data) {
        if (start >= formula.length() || end < 0 || start >= end) return 0;
        // 处理OR操作符
        int orIndex = formula.indexOf('|', start);
        if (orIndex >= 0 && orIndex < end) {
            int leftResult = parseEffectFormula(formula, start, orIndex - 1, data);
            if (leftResult > 0) {
                return leftResult;
            }

            int rightResult = parseEffectFormula(formula, orIndex + 1, end, data);
            return Math.max(rightResult, 0);
        }
        
        // 处理AND操作符
        int andIndex = formula.indexOf('&', start);
        if (andIndex >= 0 && andIndex < end) {
            int leftResult = parseEffectFormula(formula, start, andIndex - 1, data);
            if (leftResult <= 0) {
                return 0;
            }

            int rightResult = parseEffectFormula(formula, andIndex + 1, end, data);
            if (rightResult <= 0) {
                return 0;
            }

            return Math.max(leftResult, rightResult);
        }
        
        // 解析基本表达式
        boolean hasMultiplier = false;
        boolean hasNumber = false;
        boolean hasNegate = false;
        boolean hasInvert = false;
        boolean hasSubtract = false;
        byte comparisonType = -1;
        int number = 0;
        int multiplier = 0;
        int result = 0;
        for (int i = start; i < end; i++) {
            char c = formula.charAt(i);
            if (c >= '0' && c <= '9') {
                if (hasMultiplier) {
                    multiplier = c - 48;
                    hasNumber = true;
                } else {
                    number *= 10;
                    number += c - 48;
                    hasNegate = true;
                }
            } else if (c == '*') {
                hasMultiplier = true;
            } else if (c == '!') {
                if (hasNegate) {
                    result += calculateBitOperation(hasInvert, hasNumber, hasSubtract, comparisonType, number, multiplier, data);
                    hasNegate = hasNumber = hasMultiplier = hasSubtract = false;
                    number = multiplier = 0;
                    comparisonType = -1;
                }

                hasInvert = true;
            } else if (c == '-') {
                if (hasNegate) {
                    result += calculateBitOperation(hasInvert, hasNumber, hasSubtract, comparisonType, number, multiplier, data);
                    hasNegate = hasNumber = hasMultiplier = hasInvert = false;
                    number = multiplier = 0;
                    comparisonType = -1;
                }

                hasSubtract = true;
            } else if (c == '=' || c == '<' || c == '>') {
                if (hasNegate) {
                    result += calculateBitOperation(hasInvert, hasNumber, hasSubtract, comparisonType, number, multiplier, data);
                    hasNegate = hasNumber = hasMultiplier = hasSubtract = hasInvert = false;
                    number = multiplier = 0;
                }

                if (c == '=') {
                    comparisonType = 0;
                } else if (c == '<') {
                    comparisonType = 2;
                } else {
                    comparisonType = 1;
                }
            } else if (c == '+' && hasNegate) {
                result += calculateBitOperation(hasInvert, hasNumber, hasSubtract, comparisonType, number, multiplier, data);
                hasNegate = hasNumber = hasMultiplier = hasSubtract = hasInvert = false;
                number = multiplier = 0;
                comparisonType = -1;
            }
        }

        if (hasNegate) {
            result += calculateBitOperation(hasInvert, hasNumber, hasSubtract, comparisonType, number, multiplier, data);
        }

        return result;
    }

    public static List<MobEffectInstance> getPotionEffects(int damage) {
        ArrayList<MobEffectInstance> effects = new ArrayList<>();
        for (Map.Entry<Holder<MobEffect>, String> entry : EFFECT_FORMULAS.entrySet()) {
            if (entry != null) {
                String formula = entry.getValue();
                int duration = parseEffectFormula(formula, 0, formula.length(), damage);
                int amplifier = 0;
                String amplifierFormula = AMPLIFIER_FORMULAS.get(entry.getKey());
                if (amplifierFormula != null) {
                    amplifier = parseEffectFormula(amplifierFormula, 0, amplifierFormula.length(), damage);
                    if (amplifier < 0) {
                        amplifier = 0;
                    }
                }

                if (entry.getKey().value().isInstantenous()) {
                    duration = 1;
                } else {
                    duration = 1200 * (duration * 3 + (duration - 1) * 2);
                    if (NEGATIVE_EFFECTS.contains(entry.getKey())) {
                        duration >>= 1;
                    }
                }

                effects.add(new MobEffectInstance(entry.getKey(), duration, amplifier));
            }
        }

        return effects;
    }

    @SuppressWarnings("ConstantValue")
    public static int calculateNetherWartPhase1(int liquidData) {
        if ((liquidData & 0x1) == 0) {
            return liquidData;
        }

        byte highestBit = 14;
        while ((liquidData & 1 << highestBit) == 0 && highestBit >= 0) {
            highestBit--;
        }

        if (highestBit < 2 || (liquidData & 1 << highestBit - 1) != 0) {
            return liquidData;
        }

        if (highestBit >= 0) {
            liquidData &= ~(1 << highestBit);
        }

        liquidData <<= 1;
        if (highestBit >= 0) {
            liquidData |= 1 << highestBit;
            liquidData |= 1 << highestBit - 1;
        }

        return liquidData & 0x7FFF;
    }

    public static int calculateNetherWartPhase2(int liquidData) {
        byte highestBit = 14;
        while ((liquidData & 1 << highestBit) == 0 && highestBit >= 0) {
            highestBit--;
        }

        if (highestBit >= 0) {
            liquidData &= ~(1 << highestBit);
        }

        int result = 0;
        int previousResult = liquidData;
        while (previousResult != result) {
            previousResult = liquidData;
            result = 0;
            for (byte bit = 0; bit < 15; bit++) {
                boolean shouldSet = isBitSet(liquidData, bit);
                if (shouldSet) {
                    if (!isBitSet(liquidData, bit + 1) && isBitSet(liquidData, bit + 2)) {
                        shouldSet = false;
                    } else if (!isBitSet(liquidData, bit - 1) && isBitSet(liquidData, bit - 2)) {
                        shouldSet = false;
                    }
                } else {
                    shouldSet = (isBitSet(liquidData, bit - 1) && isBitSet(liquidData, bit + 1));
                }

                if (shouldSet) {
                    result |= 1 << bit;
                }
            }

            liquidData = result;
        }

        if (highestBit >= 0) {
            result |= 1 << highestBit;
        }

        return result & 0x7FFF;
    }

    public static int applyNetherWart(int data) {
        if ((data & 0x1) != 0) {
            data = calculateNetherWartPhase1(data);
        }

        return calculateNetherWartPhase2(data);
    }

    private static int modifyBit(int data, int bitPosition, boolean clear, boolean toggle) {
        if (clear) {
            data &= ~(1 << bitPosition);
        } else if (toggle) {
            if ((data & 1 << bitPosition) != 0) {
                data &= ~(1 << bitPosition);
            } else {
                data |= 1 << bitPosition;
            }
        } else {
            data |= 1 << bitPosition;
        }

        return data;
    }

    public static int applyIngredient(int data, String effectString) {
        int length = effectString.length();
        boolean hasNumber = false;
        boolean isToggle = false;
        boolean isClear = false;
        int number = 0;
        for (int i = 0; i < length; i++) {
            char c = effectString.charAt(i);
            if (c >= '0' && c <= '9') {
                number *= 10;
                number += c - 48;
                hasNumber = true;
            } else if (c == '!') {
                if (hasNumber) {
                    data = modifyBit(data, number, isClear, isToggle);
                    hasNumber = isClear = false;
                    number = 0;
                }

                isToggle = true;
            } else if (c == '-') {
                if (hasNumber) {
                    data = modifyBit(data, number, isClear, isToggle);
                    hasNumber = isToggle = false;
                    number = 0;
                }

                isClear = true;
            } else if (c == '+' && hasNumber) {
                data = modifyBit(data, number, isClear, isToggle);
                hasNumber = isClear = isToggle = false;
                number = 0;
            }
        }

        if (hasNumber) {
            data = modifyBit(data, number, isClear, isToggle);
        }

        return data & 0x7FFF;
    }

    public static int extractBinaryNumber(int data, int bit1, int bit2, int bit3, int bit4, int bit5) {
        return (checkBit(data, bit1) ? 16 : 0) | (checkBit(data, bit2) ? 8 : 0) | 
               (checkBit(data, bit3) ? 4 : 0) | (checkBit(data, bit4) ? 2 : 0) | 
               (checkBit(data, bit5) ? 1 : 0);
    }

    public static boolean isPotionIngredient(Item item) {
        return INGREDIENT_FORMULAS.containsKey(item);
    }

    public static String getPotionEffect(Item item) {
        return INGREDIENT_FORMULAS.get(item);
    }

    static {
        EFFECT_FORMULAS.put(MobEffects.MOVEMENT_SPEED, "!10 & !4 & 5*2+0 & >1 | !7 & !4 & 5*2+0 & >1");
        EFFECT_FORMULAS.put(MobEffects.MOVEMENT_SLOWDOWN, "10 & 7 & !4 & 7+5+1-0");
        EFFECT_FORMULAS.put(MobEffects.DIG_SPEED, "2 & 12+2+6-1-7 & <8");
        EFFECT_FORMULAS.put(MobEffects.DIG_SLOWDOWN, "!2 & !1*2-9 & 14-5");
        EFFECT_FORMULAS.put(MobEffects.DAMAGE_BOOST, "9 & 3 & 9+4+5 & <11");
        EFFECT_FORMULAS.put(MobEffects.HEAL, "11 & <6");
        EFFECT_FORMULAS.put(MobEffects.HARM, "!11 & 1 & 10 & !7");
        EFFECT_FORMULAS.put(MobEffects.JUMP, "8 & 2+0 & <5");
        EFFECT_FORMULAS.put(MobEffects.CONFUSION, "8*2-!7+4-11 & !2 | 13 & 11 & 2*3-1-5");
        EFFECT_FORMULAS.put(MobEffects.REGENERATION, "!14 & 13*3-!0-!5-8");
        EFFECT_FORMULAS.put(MobEffects.DAMAGE_RESISTANCE, "10 & 4 & 10+5+6 & <9");
        EFFECT_FORMULAS.put(MobEffects.FIRE_RESISTANCE, "14 & !5 & 6-!1 & 14+13+12");
        EFFECT_FORMULAS.put(MobEffects.WATER_BREATHING, "0+1+12 & !6 & 10 & !11 & !13");
        EFFECT_FORMULAS.put(MobEffects.INVISIBILITY, "2+5+13-0-4 & !7 & !1 & >5");
        EFFECT_FORMULAS.put(MobEffects.BLINDNESS, "9 & !1 & !5 & !3 & =3");
        EFFECT_FORMULAS.put(MobEffects.NIGHT_VISION, "8*2-!7 & 5 & !0 & >3");
        EFFECT_FORMULAS.put(MobEffects.HUNGER, ">4>6>8-3-8+2");
        EFFECT_FORMULAS.put(MobEffects.WEAKNESS, "=1>5>7>9+3-7-2-11 & !10 & !0");
        EFFECT_FORMULAS.put(MobEffects.POISON, "12+9 & !13 & !0");

        EFFECT_FORMULAS.put(MobEffects.SLOW_FALLING, "3+8+11 & !2 & 7+4 & <10");
        EFFECT_FORMULAS.put(MobEffects.CONDUIT_POWER, "1+6+13 & !9 & 5+12 & >7");
        EFFECT_FORMULAS.put(MobEffects.DOLPHINS_GRACE, "4+9+14 & !1 & 8+3 & <12");
        EFFECT_FORMULAS.put(MobEffects.BAD_OMEN, "7+12+0 & !5 & 11+6 & >3");
        EFFECT_FORMULAS.put(MobEffects.HERO_OF_THE_VILLAGE, "2+7+10 & !13 & 1+14 & <6");
        EFFECT_FORMULAS.put(MobEffects.DARKNESS, "5+10+1 & !8 & 0+13 & >4");
        EFFECT_FORMULAS.put(MobEffects.LEVITATION, "6+11+2 & !3 & 9+0 & <14");
        EFFECT_FORMULAS.put(MobEffects.GLOWING, "13+4+7 & !10 & 2+15 & >1");
        EFFECT_FORMULAS.put(MobEffects.ABSORPTION, "0+5+8 & !12 & 3+14 & <9");
        EFFECT_FORMULAS.put(MobEffects.SATURATION, "1+6+9 & !11 & 4+15 & <8");
        EFFECT_FORMULAS.put(MobEffects.LUCK, "2+7+10 & !13 & 5+0 & <12");
        EFFECT_FORMULAS.put(MobEffects.UNLUCK, "3+8+11 & !14 & 6+1 & <13");
//        EFFECT_FORMULAS.put(MobEffects.SLOW, "4+9+12 & !0 & 7+2 & <15");
//        EFFECT_FORMULAS.put(MobEffects.FASTER_DIG, "5+10+13 & !1 & 8+3 & <0");
//        EFFECT_FORMULAS.put(MobEffects.SLOWER_DIG, "6+11+14 & !2 & 9+4 & <1");
        EFFECT_FORMULAS.put(MobEffects.HEALTH_BOOST, "7+12+0 & !3 & 10+5 & <2");
        EFFECT_FORMULAS.put(MobEffects.WITHER, "8+13+1 & !4 & 11+6 & <3");

        AMPLIFIER_FORMULAS.put(MobEffects.MOVEMENT_SPEED, "7+!3-!1");
        AMPLIFIER_FORMULAS.put(MobEffects.DIG_SPEED, "1+0-!11");
        AMPLIFIER_FORMULAS.put(MobEffects.DAMAGE_BOOST, "2+7-!12");
        AMPLIFIER_FORMULAS.put(MobEffects.HEAL, "11+!0-!1-!14");
        AMPLIFIER_FORMULAS.put(MobEffects.HARM, "!11-!14+!0-!1");
        AMPLIFIER_FORMULAS.put(MobEffects.DAMAGE_RESISTANCE, "12-!2");
        AMPLIFIER_FORMULAS.put(MobEffects.POISON, "14>5");

        AMPLIFIER_FORMULAS.put(MobEffects.SLOW_FALLING, "3+8-!6");
        AMPLIFIER_FORMULAS.put(MobEffects.CONDUIT_POWER, "5+10-!2");
        AMPLIFIER_FORMULAS.put(MobEffects.DOLPHINS_GRACE, "7+12-!4");
        AMPLIFIER_FORMULAS.put(MobEffects.BAD_OMEN, "1+6-!9");
        AMPLIFIER_FORMULAS.put(MobEffects.HERO_OF_THE_VILLAGE, "4+11-!7");
        AMPLIFIER_FORMULAS.put(MobEffects.DARKNESS, "8+13-!1");
        AMPLIFIER_FORMULAS.put(MobEffects.LEVITATION, "2+9-!14");
        AMPLIFIER_FORMULAS.put(MobEffects.GLOWING, "6+15-!3");
        AMPLIFIER_FORMULAS.put(MobEffects.ABSORPTION, "0+5-!12");
        AMPLIFIER_FORMULAS.put(MobEffects.SATURATION, "10+15-!8");
        AMPLIFIER_FORMULAS.put(MobEffects.LUCK, "3+8-!13");
        AMPLIFIER_FORMULAS.put(MobEffects.UNLUCK, "7+12-!4");
//        AMPLIFIER_FORMULAS.put(MobEffects.SLOW, "1+6-!10");
//        AMPLIFIER_FORMULAS.put(MobEffects.FASTER_DIG, "4+11-!7");
//        AMPLIFIER_FORMULAS.put(MobEffects.SLOWER_DIG, "8+13-!2");
        AMPLIFIER_FORMULAS.put(MobEffects.HEALTH_BOOST, "5+10-!9");
        AMPLIFIER_FORMULAS.put(MobEffects.WITHER, "2+9-!14");

        INGREDIENT_FORMULAS.put(Items.GHAST_TEAR, "+11");
        INGREDIENT_FORMULAS.put(Items.BLAZE_POWDER, "+14");
        INGREDIENT_FORMULAS.put(Items.MAGMA_CREAM, "+14+6+1");
        INGREDIENT_FORMULAS.put(Items.SUGAR, "+0");
        INGREDIENT_FORMULAS.put(Items.SPIDER_EYE, "+10+7+5");
        INGREDIENT_FORMULAS.put(Items.FERMENTED_SPIDER_EYE, "+14+9");

        INGREDIENT_FORMULAS.put(Items.PHANTOM_MEMBRANE, "+3+8+11");
        INGREDIENT_FORMULAS.put(Items.NAUTILUS_SHELL, "+1+6+13");
        INGREDIENT_FORMULAS.put(Items.HEART_OF_THE_SEA, "+4+9+14");
        INGREDIENT_FORMULAS.put(Items.TOTEM_OF_UNDYING, "+7+12+0");
        INGREDIENT_FORMULAS.put(Items.EMERALD, "+2+7+10");
        INGREDIENT_FORMULAS.put(Items.AMETHYST_SHARD, "+5+10+1");
        INGREDIENT_FORMULAS.put(Items.COPPER_INGOT, "+6+11+2");
        INGREDIENT_FORMULAS.put(Items.GLOW_INK_SAC, "+13+4+7");
        INGREDIENT_FORMULAS.put(Items.GOLDEN_APPLE, "+0+5+8");
        INGREDIENT_FORMULAS.put(Items.COOKIE, "+1+6+9");
        INGREDIENT_FORMULAS.put(Items.LAPIS_LAZULI, "+2+7+10");
        INGREDIENT_FORMULAS.put(Items.REDSTONE, "+3+8+11");
        INGREDIENT_FORMULAS.put(Items.SLIME_BALL, "+4+9+12");
        INGREDIENT_FORMULAS.put(Items.BONE, "+5+10+13");
        INGREDIENT_FORMULAS.put(Items.ROTTEN_FLESH, "+6+11+14");
//        INGREDIENT_EFFECTS.put(Items.SPIDER_EYE, "+7+12+0");
        INGREDIENT_FORMULAS.put(Items.WITHER_SKELETON_SKULL, "+8+13+1");

        BuiltInRegistries.MOB_EFFECT.stream().filter(effect -> !effect.isBeneficial())
                .forEach(effect -> NEGATIVE_EFFECTS.add(Holder.direct(effect)));
    }

}