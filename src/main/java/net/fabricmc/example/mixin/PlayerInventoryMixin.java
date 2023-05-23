package net.fabricmc.example.mixin;

import net.fabricmc.example.SoulboundInitializer;
import net.fabricmc.example.SoulboundUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    /* Thanks to unascribed for the genius idea to  */
    @ModifyVariable(method = "dropAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", shift = At.Shift.BEFORE, ordinal=0))
    public ItemStack soulbound$injectPrintLine(ItemStack orig){
        if (orig.isEmpty()) {
            return ItemStack.EMPTY;
        }
        SoulboundInitializer.LOGGER.info(orig.getItem().getTranslationKey());
        if (SoulboundUtil.itemHasSoulbound(orig))
            return ItemStack.EMPTY;
        return orig;
    }
}

