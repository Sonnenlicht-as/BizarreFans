package sonnenlicht.somethinggood.client.registry;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import sonnenlicht.somethinggood.common.render.FanBlockTileEntityRenderer;

import static sonnenlicht.somethinggood.BizarreFans.MODID;

/**
 * 方块实体渲染注册
 * {@link net.minecraft.client.renderer.tileentity.TileEntityRenderer}
 */
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RenderRegistry {

	@SubscribeEvent
	public static void registerEntityRenderers(FMLClientSetupEvent event)
	{
		ClientRegistry.bindTileEntityRenderer(TileRegistry.BASE_FAN_BLOCK_TILE.get(), FanBlockTileEntityRenderer::new);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onRenderTypeSetup(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(BlockRegistry.BASE_FAN_BLOCK.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(BlockRegistry.FUNCTION_FAN_BLOCK.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(BlockRegistry.FIRE_FAN_BLOCK.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(BlockRegistry.FROZEN_FAN_BLOCK.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(BlockRegistry.PULL_FAN_BLOCK.get(), RenderType.getTranslucent());
	}
}