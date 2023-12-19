package com.leisure.duncraw.art.chara;

import com.leisure.duncraw.data.CharasData;
import com.leisure.duncraw.helper.Instantiator;
import com.leisure.duncraw.manager.CharaManager;

import behave.models.Node;

public class EnemySpawner extends Spawner {
  private Instantiator<Node> commonAi;
  public EnemySpawner(CharaManager charaManager, CharasData sources, Instantiator<Node> ai) {
    super(charaManager, sources);
    this.commonAi = ai;
  }
  @Override
  public Chara spawn(String source) {
    Enemy enemy = charaManager.addFrom(source, Enemy.class);
    enemy.startAI(commonAi.instance());
    return enemy;
  }
}
