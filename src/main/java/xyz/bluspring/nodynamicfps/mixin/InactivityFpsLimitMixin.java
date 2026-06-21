package xyz.bluspring.nodynamicfps.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.InactivityFpsLimit;

@Mixin(InactivityFpsLimit.class)
public enum InactivityFpsLimitMixin {
    NO_DYNAMIC_FPS_DISABLED("disabled", "options.inactivityFpsLimit.disabled");

    @Shadow
    InactivityFpsLimitMixin(final String serializedName, final String key) {}
}
