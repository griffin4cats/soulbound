package net.fabricmc.example;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SoulboundUtil {
    public static final NbtCompound soulboundCompound = giveSoulbound(new NbtCompound());

    public static boolean hasSoulbound(NbtCompound itemCompound) {
        if (itemCompound.contains("Soulbound", NbtElement.BYTE_TYPE))
            return itemCompound.getByte("Soulbound") == 1;
        return false;
    }

    public static boolean itemHasSoulbound(ItemStack item){
        if (!item.hasNbt())
            return false;
        return hasSoulbound(item.getNbt());
    }

    public static NbtCompound safeGiveSoulbound(NbtCompound thisCompound){
        return hasSoulbound(thisCompound) ? thisCompound : giveSoulbound(thisCompound);
    }

    public static NbtCompound giveSoulbound(NbtCompound thisCompound){
        thisCompound.putByte("Soulbound", (byte) 1);
        if (!thisCompound.contains("display", NbtElement.COMPOUND_TYPE))
            thisCompound.put("display", new NbtCompound());
        NbtCompound display = thisCompound.getCompound("display");
        NbtList displayList = display.getList("Lore", NbtElement.STRING_TYPE);
        displayList.add(NbtString.of((Text.Serializer.toJson(Text.literal("Soulbound... I hope?").formatted(Formatting.GRAY)))));
        display.put("Lore", displayList);
        return thisCompound;
    }
}
