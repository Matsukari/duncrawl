package com.leisure.duncraw.map;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.art.map.Terrain;

public class TerrainVariants extends ArrayList<Terrain> {
  public TerrainVariants() {}
  public TerrainVariants(ArrayList<Terrain> variants) { addAll(variants); }
  public Terrain getVariant() { return get(MathUtils.random(size()-1)).clone(); }
  
}
