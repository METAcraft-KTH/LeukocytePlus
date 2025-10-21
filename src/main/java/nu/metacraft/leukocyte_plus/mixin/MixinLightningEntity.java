package nu.metacraft.leukocyte_plus.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import nu.metacraft.leukocyte_plus.EventHelper;
import nu.metacraft.leukocyte_plus.events.FireEvents;

@Mixin(LightningEntity.class)
public abstract class MixinLightningEntity extends Entity {

	public MixinLightningEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	@Shadow @Nullable public abstract ServerPlayerEntity getChanneler();

	@Inject(method = "spawnFire", at = @At("HEAD"), cancellable = true)
	public void spawnFire(int spreadAttempts, CallbackInfo ci) {
		if (!EventHelper.shouldPlaceFire(true, FireEvents.LIGHTNING, this, this.getChanneler())) {
			ci.cancel();
		}
	}

}
