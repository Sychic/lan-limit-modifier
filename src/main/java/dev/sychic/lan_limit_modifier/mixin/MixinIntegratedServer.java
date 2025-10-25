package dev.sychic.lan_limit_modifier.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.server.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IntegratedServer.class)
public class MixinIntegratedServer {
    @ModifyReturnValue(method = "getMaxPlayers", at = @At("RETURN"))
    private int modifyMaxPlayers(int original) {
        if (!System.getProperty("lanlimit.value").isEmpty()) {
            return Integer.parseInt(System.getProperty("lanlimit.value"));
        }
        return original;
    }
}
