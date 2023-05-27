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
	public static final Logger LOGGER = LoggerFactory.getLogger("soulbound");

	@Override
	public void onInitialize() {
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
						.weight(1)
						.apply(SetNbtLootFunction.builder(SoulboundUtil.strongSoulboundCompound)))
					.with(ItemEntry.builder(Items.AIR).weight(3));
				builder.pool(pb);
			}
			if (LootTables.ANCIENT_CITY_CHEST.equals(identifier)) {
				LootPool.Builder pb = LootPool.builder()
						.with(ItemEntry.builder(Items.ENCHANTED_BOOK)
								.weight(1)
								.apply(SetNbtLootFunction.builder(SoulboundUtil.weakSoulboundCompound)))
						.with(ItemEntry.builder(Items.AIR).weight(9));
				builder.pool(pb);
			}
			if (LootTables.NETHER_BRIDGE_CHEST.equals(identifier)) {
				LootPool.Builder pb = LootPool.builder()
					.with(ItemEntry.builder(Items.ENCHANTED_BOOK)
						.weight(1)
						.apply(SetNbtLootFunction.builder(SoulboundUtil.weakSoulboundCompound)))
					.with(ItemEntry.builder(Items.AIR).weight(1));
				builder.pool(pb);
			}
			if (LootTables.BURIED_TREASURE_CHEST.equals(identifier)) {
				LootPool.Builder pb = LootPool.builder()
						.with(ItemEntry.builder(Items.ENCHANTED_BOOK)
								.weight(1)
								.apply(SetNbtLootFunction.builder(SoulboundUtil.weakSoulboundCompound)))
						.with(ItemEntry.builder(Items.AIR).weight(1));
				builder.pool(pb);
			}
			if (LootTables.RUINED_PORTAL_CHEST.equals(identifier)) {
				LootPool.Builder pb = LootPool.builder()
						.with(ItemEntry.builder(Items.ENCHANTED_BOOK)
								.weight(1)
								.apply(SetNbtLootFunction.builder(SoulboundUtil.weakSoulboundCompound)))
						.with(ItemEntry.builder(Items.AIR).weight(4));
				builder.pool(pb);
			}
		});
	}
}
