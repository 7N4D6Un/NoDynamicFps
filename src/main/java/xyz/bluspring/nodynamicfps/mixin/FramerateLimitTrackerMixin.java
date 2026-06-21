package xyz.bluspring.nodynamicfps.mixin;

import com.mojang.blaze3d.platform.FramerateLimitTracker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.InactivityFpsLimit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;

@Mixin(FramerateLimitTracker.class)
public abstract class FramerateLimitTrackerMixin {
    @Shadow @Final private Options options;
    @Shadow @Final private Minecraft minecraft;
    @Shadow private int framerateLimit;

    @Inject(method = "getFramerateLimit", at = @At("HEAD"), cancellable = true)
    private void disableDynamicFps(CallbackInfoReturnable<Integer> cir) {
        var limitType = this.options.inactivityFpsLimit().get();

        if (limitType == InactivityFpsLimit.NO_DYNAMIC_FPS_DISABLED) {
            cir.setReturnValue(this.minecraft.level != null || this.minecraft.gui.screen() == null && this.minecraft.gui.overlay() == null ? this.framerateLimit : 60);
        }
    }
}
