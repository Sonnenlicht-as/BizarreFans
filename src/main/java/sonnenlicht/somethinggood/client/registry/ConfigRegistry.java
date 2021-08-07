package sonnenlicht.somethinggood.client.registry;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigRegistry {

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.DoubleValue FAN_FORCE;
    public static ForgeConfigSpec.IntValue FAN_RANGE;
    public static ForgeConfigSpec.BooleanValue PLAY_SOUND;
    public static ForgeConfigSpec.BooleanValue SUMMON_PARTICLE;
    public static ForgeConfigSpec.IntValue FUNCTION_FAN_DAMAGE;
    public static ForgeConfigSpec.IntValue FIRE_FAN_DAMAGE;
    public static ForgeConfigSpec.IntValue ABSORB_ZONE;
    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("General Settings");
        COMMON_BUILDER.push("General");

        //所有风扇的风力，风力为基础值的倍率，默认是1倍原速
        FAN_FORCE = COMMON_BUILDER.comment("The force of the fan")
                .defineInRange("force", 1.0, 0, 5.0);

        //所有正常风扇的风力直线范围，默认值为0，默认范围为3x7x3，修改此值后范围为3x(7+修改值)x3，数值范围为0-10
        FAN_RANGE = COMMON_BUILDER.comment("The line range of the fan. The default value is 0 and the range is 0-10, before:3x7x3, after:3x(7+value)x3")
                .defineInRange("range", 0, 0, 10);

        //多功能风扇“风针”的伤害，默认值为1，数值范围为0-50
        FUNCTION_FAN_DAMAGE = COMMON_BUILDER.comment("The damage of the function-fan mode 'WIND SHOT'. The default value is 1 and the range is 1-50")
                .defineInRange("function_fan_damage", 1, 1, 50);

        //多功能风扇吸收物品和XP的范围，为数值X数值X数值的正方体范围，默认值为9，数值范围为1-15
        ABSORB_ZONE = COMMON_BUILDER.comment("The range of function-fan absorbing items and XP, the cube range of x. The default value is 9 and the range is 1-15")
                .defineInRange("function_fan_zone", 9, 1, 15);

        //高温风扇的额外伤害，默认值为1，数值范围为0-50
        FIRE_FAN_DAMAGE = COMMON_BUILDER.comment("The extra damage of the fire-fan. The default value is 1 and the range is 0-50")
                .defineInRange("fire_fan_damage", 1, 0, 50);

        //风扇运行时是否发出声音，默认为true
        PLAY_SOUND = COMMON_BUILDER.comment("Play the sound of the working fan")
                .define("play_fan_sound", true);

        //在风扇运行时是否产生粒子，默认为true
        SUMMON_PARTICLE = COMMON_BUILDER.comment("Spawn the particle of the working fan")
                .define("spawn_fan_particle", true);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
