package sonnenlicht.somethinggood.common.mode;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public enum FanMode implements IStringSerializable {

   NORMAL("normal"),
   XP("xp"),
   ITEM("item"),
   DAMAGE("damage");

   private final String name;

   private final ITextComponent textComponent;

   FanMode(String name) {
      this.name = name;
      this.textComponent = new TranslationTextComponent("fan.mode_info." + name);
   }

   @Nonnull
   public String getString() {
      return this.name;
   }

   @OnlyIn(Dist.CLIENT)
   public ITextComponent getTextComponent() {
      return this.textComponent;
   }

}