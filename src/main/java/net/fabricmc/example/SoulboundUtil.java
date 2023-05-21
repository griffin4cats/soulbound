package net.fabricmc.example;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;

public class SoulboundUtil {

    public static boolean hasSoulbound(ItemStack item) {
        if (item.hasNbt()) {
            if (item.getNbt().contains("Soulbound", NbtElement.BYTE_TYPE))
                return item.getNbt().getByte("Soulbound") == 1;
        }
        return false;
    }
}
