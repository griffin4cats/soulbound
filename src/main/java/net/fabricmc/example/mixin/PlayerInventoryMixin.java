package net.fabricmc.example.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @ModifyVariable(method = "dropAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", shift = At.Shift.BEFORE, ordinal=0))
    public ItemStack soulbound$injectPrintLine(ItemStack orig){
        if (orig.getItem().getName().getString().equalsIgnoreCase("Diamond")){
            System.out.println("diamond retained :)");
            return ItemStack.EMPTY;
        }
        System.out.println(orig.getItem().getName().getString());
        return orig;
    }
}

