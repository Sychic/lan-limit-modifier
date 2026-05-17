package dev.sychic.lan_limit_modifier.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.datafixers.DataFixer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.progress.LevelLoadListener;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.net.Proxy;
import java.util.Optional;

@Mixin(IntegratedServer.class)
public abstract class MixinIntegratedServer extends MinecraftServer {
    public MixinIntegratedServer(Thread serverThread, LevelStorageSource.LevelStorageAccess storageSource, PackRepository packRepository, WorldStem worldStem, Optional<GameRules> gameRules, Proxy proxy, DataFixer fixerUpper, Services services, LevelLoadListener levelLoadListener, boolean propagatesCrashes) {
        super(serverThread, storageSource, packRepository, worldStem, gameRules, proxy, fixerUpper, services, levelLoadListener, propagatesCrashes);
    }

    @ModifyReturnValue(method = "getMaxPlayers", at = @At("RETURN"))
    private int modifyMaxPlayers(int original) {
        if (System.getProperty("lanlimit.value") != null) {
            return Integer.parseInt(System.getProperty("lanlimit.value"));
        }
        return this.getPlayerCount() + 1;
    }
}
