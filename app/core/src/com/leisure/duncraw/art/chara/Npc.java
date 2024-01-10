package com.leisure.duncraw.art.chara;

import java.util.ArrayList;

import com.leisure.duncraw.art.chara.observers.TalkBehaviour;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.Conversation;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.hud.DialogueHud;
import com.leisure.duncraw.logging.Logger;

public class Npc extends Chara {  
  public NpcData npcData;
  public String datFile;
  public static class NpcData extends CharaData {
    public ArrayList<String> convs;
    @Override
    public void reset() {
      super.reset();
      convs = new ArrayList<>();
    }
  }
  public Npc(String datFile, DialogueHud dialogueHud) {
    this.datFile = datFile;
    npcData = Deserializer.safeLoad(NpcData.class, datFile);
    init(npcData);
    ArrayList<Conversation> conversations = new ArrayList<>();
    for (String conv : npcData.convs) {
      conversations.add(Deserializer.safeLoad(Conversation.class, conv));
      Logger.log("Npc", "Got " + conv);

    }
    observers.add(new TalkBehaviour(dialogueHud, conversations));
  }
}
