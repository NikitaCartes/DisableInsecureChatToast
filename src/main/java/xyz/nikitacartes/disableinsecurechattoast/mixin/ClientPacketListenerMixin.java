package xyz.nikitacartes.disableinsecurechattoast.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Redirect(method = "handleLogin(Lnet/minecraft/network/protocol/game/ClientboundLoginPacket;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/components/toasts/ToastManager;addToast(Lnet/minecraft/client/gui/components/toasts/Toast;)V"))
    private void cancelAdding(final ToastManager instance, final Toast toast) {
    }
}
