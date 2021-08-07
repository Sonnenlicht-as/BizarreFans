package sonnenlicht.somethinggood.client.registry;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sonnenlicht.somethinggood.common.block.*;

import static sonnenlicht.somethinggood.BizarreFans.MODID;

/**
 * 方块注册
 * {@link Block}
 */
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<Block> BASE_FAN_BLOCK = BLOCKS.register("base_fan_block", BaseFanBlock::new);

    public static final RegistryObject<Block> FROZEN_FAN_BLOCK = BLOCKS.register("frozen_fan_block", FrozenFanBlock::new);

    public static final RegistryObject<Block> FIRE_FAN_BLOCK = BLOCKS.register("fire_fan_block", FireFanBlock::new);

    public static final RegistryObject<Block> FUNCTION_FAN_BLOCK = BLOCKS.register("function_fan_block", MoreFunctionFanBlock::new);

    public static final RegistryObject<Block> PULL_FAN_BLOCK = BLOCKS.register("pull_fan_block", PullFanBlock::new);
}
