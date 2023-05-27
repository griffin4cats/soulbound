package net.fabricmc.example;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

public class SoulboundUtil {
    public static final String strongEnchantmentDisplay = "{\"text\": \"§5§lSoulbound\"}";
    public static final String strongSoulboundKey = "StrongSoulbound";
    public static final String weakEnchantmentDisplay = "{\"text\": \"§5§lWeakly soulbound\"}";
    public static final String weakSoulboundKey = "WeakSoulbound";
    public static final NbtCompound strongSoulboundCompound = giveStrongSoulbound(new NbtCompound());
    public static final NbtCompound weakSoulboundCompound = giveWeakSoulbound(new NbtCompound());

    //Death handler. Because of how the mixin works, this returns an empty stack if item has soulbound.
    public static ItemStack soulboundDeathHandler(ItemStack orig){
        if (orig.isEmpty())
            return ItemStack.EMPTY;
        if (!orig.hasNbt())
            return orig;
        if (itemHasStrongSoulbound(orig)) {
            SoulboundInitializer.LOGGER.info("strong soulbound found :D");
            return ItemStack.EMPTY;
        }
        if (itemHasWeakSoulbound(orig)) {
            SoulboundInitializer.LOGGER.info("weak soulbound found :)");
            orig.setNbt(withoutWeakSoulbound(orig.getNbt()));
            return ItemStack.EMPTY;
        }
        return orig;
    }

    public static boolean hasStrongSoulbound(NbtCompound itemCompound) {
        if (itemCompound.contains(strongSoulboundKey, NbtElement.BYTE_TYPE))
            return itemCompound.getByte(strongSoulboundKey) == 1;
        return false;
    }

    public static boolean itemHasStrongSoulbound(ItemStack item){
        if (!item.hasNbt())
            return false;
        return hasStrongSoulbound(item.getNbt());
    }

    public static boolean hasWeakSoulbound(NbtCompound itemCompound) {
        if (itemCompound.contains(weakSoulboundKey, NbtElement.BYTE_TYPE))
            return itemCompound.getByte(weakSoulboundKey) == 1;
        return false;
    }

    public static boolean itemHasWeakSoulbound(ItemStack item){
        if (!item.hasNbt())
            return false;
        return hasWeakSoulbound(item.getNbt());
    }


    public static NbtCompound giveStrongSoulbound(NbtCompound thisCompound){
        thisCompound.putByte(strongSoulboundKey, (byte) 1);
        if (!thisCompound.contains("display", NbtElement.COMPOUND_TYPE))
            thisCompound.put("display", new NbtCompound());
        NbtCompound display = thisCompound.getCompound("display");
        NbtList displayList = display.getList("Lore", NbtElement.STRING_TYPE);
        displayList.add(NbtString.of(strongEnchantmentDisplay));
        display.put("Lore", displayList);
        return thisCompound;
    }

    public static NbtCompound giveWeakSoulbound(NbtCompound thisCompound){
        thisCompound.putByte(weakSoulboundKey, (byte) 1);
        if (!thisCompound.contains("display", NbtElement.COMPOUND_TYPE))
            thisCompound.put("display", new NbtCompound());
        NbtCompound display = thisCompound.getCompound("display");
        NbtList displayList = display.getList("Lore", NbtElement.STRING_TYPE);
        displayList.add(NbtString.of(weakEnchantmentDisplay));
        display.put("Lore", displayList);
        return thisCompound;
    }

    public static NbtCompound withoutWeakSoulbound(NbtCompound thisCompound){
        if (thisCompound.contains(weakSoulboundKey))
            thisCompound.remove(weakSoulboundKey);
        if (!thisCompound.contains("display", NbtElement.COMPOUND_TYPE)) {
            return thisCompound;
        }
        NbtCompound display = thisCompound.getCompound("display");
        NbtList displayList = display.getList("Lore", NbtElement.STRING_TYPE);
        for (int i = 0; i < displayList.size(); i++){
            if (displayList.get(i).asString().startsWith(weakEnchantmentDisplay)){
                displayList.remove(i);
            }
        }
        display.put("Lore", displayList);
        thisCompound.put("display", display);
        return thisCompound;
    }
}
