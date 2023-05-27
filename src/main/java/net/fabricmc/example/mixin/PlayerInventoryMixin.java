package net.fabricmc.example.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import static net.fabricmc.example.SoulboundUtil.soulboundDeathHandler;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    /* Thanks to unascribed for the genius idea to  */
    @ModifyVariable(method = "dropAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", shift = At.Shift.BEFORE, ordinal=0))
    public ItemStack soulbound$injectItemReturn(ItemStack orig){
        return soulboundDeathHandler(orig);
    }
}

