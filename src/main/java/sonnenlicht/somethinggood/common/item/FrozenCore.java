package sonnenlicht.somethinggood.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;

import static sonnenlicht.somethinggood.common.FanGroup.FAN_GROUP;

@SuppressWarnings("all")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrozenCore extends Item {

    public FrozenCore() {
        super(new Properties().group(FAN_GROUP).maxStackSize(64));
    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("bizarre_fans.frozen_core.desc_0"));
    }
}
