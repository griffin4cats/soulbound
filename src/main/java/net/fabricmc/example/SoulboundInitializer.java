package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetNbtLootFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoulboundInitializer implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("soulbound");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Soulbound enabled");
		ServerPlayerEvents.COPY_FROM.register((oldp, newp, alive) -> {
			newp.getInventory().clone(oldp.getInventory());
		});

		LootTableEvents.MODIFY.register((resourceManager, lootManager, identifier, builder, lootTableSource) -> {
			if (!lootTableSource.isBuiltin()) {
				return;
			}
			if (LootTables.ANCIENT_CITY_CHEST.equals(identifier)) {
				LootPool.Builder pb = LootPool.builder()
					.with(ItemEntry.builder(Items.ENCHANTED_BOOK)
						.weight(100)
						.apply(SetNbtLootFunction.builder(SoulboundUtil.soulboundCompound)))
					.with(ItemEntry.builder(Items.AIR).weight(1));
				builder.pool(pb);
			}
		});
	}
}
