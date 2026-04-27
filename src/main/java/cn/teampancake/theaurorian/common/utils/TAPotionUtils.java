package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.common.datamaps.AlchemyTableMaterial;
import cn.teampancake.theaurorian.common.registry.TADataMaps;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.*;

public class TAPotionUtils {

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
        int orIndex = formula.indexOf('|', start);
        if (orIndex >= 0 && orIndex < end) {
            int leftResult = parseEffectFormula(formula, start, orIndex - 1, data);
            if (leftResult > 0) {
                return leftResult;
            }

            int rightResult = parseEffectFormula(formula, orIndex + 1, end, data);
            return Math.max(rightResult, 0);
        }

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
        ArrayList<MobEffectInstance> effects = null;
        for (Holder.Reference<MobEffect> reference : BuiltInRegistries.MOB_EFFECT.holders().toList()) {
            AlchemyTableMaterial usableEffectData = reference.getData(TADataMaps.ALCHEMY_TABLE_USABLE_EFFECTS);
            if (usableEffectData != null) {
                String formula = usableEffectData.formula();
                int duration = parseEffectFormula(formula, 0, formula.length(), damage);
                if (duration > 0) {
                    int amplifier = 0;
                    AlchemyTableMaterial amplifierEffectData = reference.getData(TADataMaps.ALCHEMY_TABLE_AMPLIFIER_EFFECTS);
                    if (amplifierEffectData != null) {
                        String amplifierFormula = amplifierEffectData.formula();
                        if (amplifierFormula != null && !amplifierFormula.isEmpty()) {
                            amplifier = parseEffectFormula(amplifierFormula, 0, amplifierFormula.length(), damage);
                            if (amplifier < 0) {
                                amplifier = 0;
                            }
                        }
                    }

                    if (reference.value().isInstantenous()) {
                        duration = 1;
                    } else {
                        duration = 1200 * (duration * 3 + (duration - 1) * 2);
                        if (!reference.value().isBeneficial()) {
                            duration >>= 1;
                        }
                    }

                    if (effects == null) {
                        effects = new ArrayList<>();
                    }

                    effects.add(new MobEffectInstance(reference, duration, amplifier));
                }
            }
        }

        return effects == null ? new ArrayList<>() : effects;
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

}