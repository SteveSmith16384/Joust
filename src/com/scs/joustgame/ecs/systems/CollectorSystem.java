package com.scs.joustgame.ecs.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.joustgame.JoustMain;
import com.scs.joustgame.ecs.components.CollectableComponent;
import com.scs.joustgame.ecs.components.PlayersAvatarComponent;

public class CollectorSystem {

	private JoustMain game;

	public CollectorSystem(JoustMain _game) {
		game = _game;
	}


	public void entityCollected(AbstractEntity collector, AbstractEntity coin) {
		coin.remove();

		CollectableComponent cc = (CollectableComponent)coin.getComponent(CollectableComponent.class);
		switch (cc.type) {
		case Coin:
			PlayersAvatarComponent uic = (PlayersAvatarComponent)collector.getComponent(PlayersAvatarComponent.class);
			//game.sfx.play("Retro_Game_Sounds_SFX_01.ogg");
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
