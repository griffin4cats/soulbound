package net.fabricmc.example.mixin;

import net.fabricmc.example.SoulboundUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler{
    @Final @Shadow private Property levelCost;

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1), cancellable = true)
    private void soulbound$anvilRecipe(CallbackInfo ci) {
        ItemStack first = this.input.getStack(0);
        ItemStack second = this.input.getStack(1);
        boolean firstIsBook = first.isOf(Items.ENCHANTED_BOOK);
        boolean secondIsBook = second.isOf(Items.ENCHANTED_BOOK);
        if (!(firstIsBook || secondIsBook)) return;
        ItemStack book = firstIsBook ? first.copy() : second.copy();
        ItemStack item = firstIsBook ? second.copy() : first.copy();
        if (SoulboundUtil.itemHasStrongSoulbound(item) || SoulboundUtil.itemHasWeakSoulbound(item)) return;
        if (SoulboundUtil.itemHasStrongSoulbound(book)) {
            ItemStack result = item.copy();
            result.setNbt(SoulboundUtil.giveStrongSoulbound(item.getOrCreateNbt()));
            this.output.setStack(0, result);
            this.levelCost.set(15);
            this.sendContentUpdates();
            ci.cancel();
        } else if (SoulboundUtil.itemHasWeakSoulbound(book)) {
            ItemStack result = item.copy();
            result.setNbt(SoulboundUtil.giveWeakSoulbound(item.getOrCreateNbt()));
            this.output.setStack(0, result);
            this.levelCost.set(15);
            this.sendContentUpdates();
            ci.cancel();
        }
    }
}
