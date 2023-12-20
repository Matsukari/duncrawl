package com.leisure.duncraw.data;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class Conversation extends Dat {
  public ArrayList<CharaMessage> messages;
  transient public CharaMessage currMessage;
  transient public int currIndex = -1;
  public CharaMessage next() {
    currIndex += 1;
    if (hasEnded()) currMessage = null;
    else currMessage = messages.get(currIndex);
    return currMessage;
  }
  public static Conversation fromDat(String source) {
    Conversation conversation = new Conversation();
    conversation.reset();
    try { conversation = Deserializer.load(Conversation.class, Gdx.files.local(source)); } 
    catch (Exception e) { Serializer.save(conversation, Gdx.files.local(source)); }
    return conversation;
  } 
  public void start() {
    restart();
    next();
  }
  public void restart() {
    currIndex = -1;
  }
  public boolean hasEnded() { return currIndex >= messages.size(); }
  @Override
  public void reset() {
    messages = new ArrayList<>();
    CharaMessage message = new CharaMessage();
    message.reset();
    message.message = "Sample message test";
    messages.add(message);
    
  }
}
