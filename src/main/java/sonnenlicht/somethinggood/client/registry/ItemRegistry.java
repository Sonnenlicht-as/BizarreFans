package sonnenlicht.somethinggood.client.registry;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sonnenlicht.somethinggood.common.item.FireCore;
import sonnenlicht.somethinggood.common.item.FrozenCore;
import sonnenlicht.somethinggood.common.item.FunctionCore;
import sonnenlicht.somethinggood.common.item.ReversalCore;

import static sonnenlicht.somethinggood.BizarreFans.MODID;
import static sonnenlicht.somethinggood.common.FanGroup.FAN_GROUP;

/**
 * 物品注册
 * {@link Item}
 */
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> BASE_FAN_BLOCK_ITEM = ITEMS.register("base_fan_block", () -> new BlockItem(BlockRegistry.BASE_FAN_BLOCK.get(),new Item.Properties().group(FAN_GROUP)));

    public static final RegistryObject<Item> FROZEN_FAN_BLOCK_ITEM = ITEMS.register("frozen_fan_block", () -> new BlockItem(BlockRegistry.FROZEN_FAN_BLOCK.get(),new Item.Properties().group(FAN_GROUP)));

    public static final RegistryObject<Item> FIRE_FAN_BLOCK_ITEM = ITEMS.register("fire_fan_block", () -> new BlockItem(BlockRegistry.FIRE_FAN_BLOCK.get(),new Item.Properties().group(FAN_GROUP)));

    public static final RegistryObject<Item> FUNCTION_FAN_BLOCK_ITEM = ITEMS.register("function_fan_block", () -> new BlockItem(BlockRegistry.FUNCTION_FAN_BLOCK.get(),new Item.Properties().group(FAN_GROUP)));

    public static final RegistryObject<Item> PULL_FAN_BLOCK_ITEM = ITEMS.register("pull_fan_block", () -> new BlockItem(BlockRegistry.PULL_FAN_BLOCK.get(),new Item.Properties().group(FAN_GROUP)));

    public static final RegistryObject<Item> FROZEN_CORE = ITEMS.register("frozen_core", FireCore::new);

    public static final RegistryObject<Item> FIRE_CORE = ITEMS.register("fire_core", FrozenCore::new);

    public static final RegistryObject<Item> FUNCTION_CORE = ITEMS.register("function_core", FunctionCore::new);

    public static final RegistryObject<Item> REVERSAL_CORE = ITEMS.register("reversal_core", ReversalCore::new);

}
