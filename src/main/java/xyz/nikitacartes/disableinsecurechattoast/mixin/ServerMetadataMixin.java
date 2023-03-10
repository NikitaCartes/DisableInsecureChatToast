package xyz.nikitacartes.disableinsecurechattoast.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.ServerMetadata;

@Mixin(ServerMetadata.class)
public class ServerMetadataMixin {

    @Inject(method = "isSecureChatEnforced()Z", at = @At("HEAD"), cancellable = true)
    public void onSecureChatCheck(CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(true);
    }

}