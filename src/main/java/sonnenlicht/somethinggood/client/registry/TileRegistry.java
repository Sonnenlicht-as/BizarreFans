package sonnenlicht.somethinggood.client.registry;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sonnenlicht.somethinggood.common.tile.*;

import static sonnenlicht.somethinggood.BizarreFans.MODID;

/**
 * 方块实体注册
 * {@link net.minecraft.tileentity.TileEntity}
 */
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileRegistry {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);

    public static final RegistryObject<TileEntityType<BaseFanBlockTileEntity>> BASE_FAN_BLOCK_TILE = TILE_ENTITIES.register("base_fan_block_tile",
            () -> TileEntityType.Builder.create(BaseFanBlockTileEntity::new,
                    BlockRegistry.BASE_FAN_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<FrozenFanBlockTileEntity>> FROZEN_FAN_BLOCK_TILE = TILE_ENTITIES.register("frozen_fan_block_tile",
            () -> TileEntityType.Builder.create(FrozenFanBlockTileEntity::new,
                    BlockRegistry.FROZEN_FAN_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<FireFanBlockTileEntity>> FIRE_FAN_BLOCK_TILE = TILE_ENTITIES.register("fire_fan_block_tile",
            () -> TileEntityType.Builder.create(FireFanBlockTileEntity::new,
                    BlockRegistry.FIRE_FAN_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<MoreFunctionFanBlockTileEntity>> FUNCTION_FAN_BLOCK_TILE = TILE_ENTITIES.register("function_fan_block_tile",
            () -> TileEntityType.Builder.create(MoreFunctionFanBlockTileEntity::new,
                    BlockRegistry.FUNCTION_FAN_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<PullFanBlockTileEntity>> PULL_FAN_BLOCK_TILE = TILE_ENTITIES.register("pull_fan_block_tile",
            () -> TileEntityType.Builder.create(PullFanBlockTileEntity::new,
                    BlockRegistry.PULL_FAN_BLOCK.get()).build(null));
}
