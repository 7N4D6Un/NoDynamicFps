package xyz.bluspring.nodynamicfps.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.InactivityFpsLimit;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

@Mixin(Options.class)
public abstract class OptionsMixin {
    @Unique private static final Component INACTIVITY_FPS_LIMIT_TOOLTIP_DISABLED = Component.translatable("options.inactivityFpsLimit.disabled.tooltip");

    @Inject(method = "lambda$new$11", at = @At("HEAD"), cancellable = true)
    private static void useDisabledTooltip(InactivityFpsLimit value, CallbackInfoReturnable<Tooltip> cir) {
        if (value == InactivityFpsLimit.NO_DYNAMIC_FPS_DISABLED) {
            cir.setReturnValue(Tooltip.create(INACTIVITY_FPS_LIMIT_TOOLTIP_DISABLED));
        }
    }

    @ModifyExpressionValue(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/InactivityFpsLimit;AFK:Lnet/minecraft/client/InactivityFpsLimit;", opcode = Opcodes.GETSTATIC),
        slice = @Slice(
            from = @At(value = "CONSTANT", args = "stringValue=options.inactivityFpsLimit"),
            to = @At(value = "CONSTANT", args = "stringValue=options.renderClouds")
        ))
    private InactivityFpsLimit setDefaultInactivityFps(InactivityFpsLimit original) {
        return InactivityFpsLimit.NO_DYNAMIC_FPS_DISABLED;
    }
}
