package sonnenlicht.somethinggood;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sonnenlicht.somethinggood.client.registry.BlockRegistry;
import sonnenlicht.somethinggood.client.registry.ConfigRegistry;
import sonnenlicht.somethinggood.client.registry.ItemRegistry;
import sonnenlicht.somethinggood.client.registry.TileRegistry;

import java.util.stream.Collectors;


@Mod("bizarre_fans")
public class BizarreFans {
    public static final String MODID = "bizarre_fans";
    private static final Logger LOGGER = LogManager.getLogger();

    public BizarreFans() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

        TileRegistry.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigRegistry.COMMON_CONFIG);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Server and Client setup");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}",
                event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("Server starting");
    }

}
