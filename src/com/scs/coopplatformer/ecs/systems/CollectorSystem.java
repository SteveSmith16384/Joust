package com.scs.coopplatformer.ecs.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.coopplatformer.MyGdxGame;
import com.scs.coopplatformer.ecs.components.CollectableComponent;
import com.scs.coopplatformer.ecs.components.PlayersAvatarComponent;

public class CollectorSystem {

	private MyGdxGame game;

	public CollectorSystem(MyGdxGame _game) {
		game = _game;
	}


	public void entityCollected(AbstractEntity collector, AbstractEntity coin) {
		coin.remove();

		CollectableComponent cc = (CollectableComponent)coin.getComponent(CollectableComponent.class);
		switch (cc.type) {
		case Coin:
			PlayersAvatarComponent uic = (PlayersAvatarComponent)collector.getComponent(PlayersAvatarComponent.class);
			game.sfx.play("Retro_Game_Sounds_SFX_01.ogg");
			if (uic != null) {
				uic.player.score += 100;
			}
			game.ecs.addEntity(game.entityFactory.createRisingCoin(coin));
			break;
		default:
			throw new RuntimeException("Unknown collectable type: " + cc.type);
		}
	}

}
