package lod.stardustindicators;

import legend.core.gte.MV;
import legend.game.EngineStateEnum;
import legend.game.modding.events.RenderEvent;
import legend.game.modding.events.gamestate.GameLoadedEvent;
import legend.game.modding.events.submap.SubmapObjectTextureEvent;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.legendofdragoon.modloader.Mod;
import org.legendofdragoon.modloader.events.EventListener;
import org.legendofdragoon.modloader.registries.RegistryId;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

import static legend.core.GameEngine.*;
import static legend.game.Scus94491BpeSegment_8004.engineState_8004dd20;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.combat.SEffe.transformWorldspaceToScreenspace;

@Mod(id = Main.MOD_ID, version = "3.0.0")
public class Main {
  public static final String MOD_ID = "stardustindicators";
  private final ArrayList<Vector3f> transforms;
  private final ArrayList<Integer> flags; // will be used once script flag events are a thing
  private final MV cacheMV;
  private AnimatedSprite stardustIndicator;

  public Main() {
    EVENTS.register(this);
    this.flags = new ArrayList<>();
    this.transforms = new ArrayList<>();
    cacheMV = new MV();
  }

  public static RegistryId id(final String entryId) {
    return new RegistryId(MOD_ID, entryId);
  }

  @EventListener
  public void loadStardusts(final SubmapObjectTextureEvent event) {
    this.transforms.clear();
    this.flags.clear();
    final StardustInfoStruct[] stardustInfos = this.stardustData.get(event.submapCut);
    if(stardustInfos == null) { return; }
    for (StardustInfoStruct stardustInfo : stardustInfos) {
      // if already collected
      if(gameState_800babc8.scriptFlags2_bc.get(stardustInfo.flag())) { return ;}

      this.transforms.add(new Vector3f(stardustInfo.x(), stardustInfo.y(), stardustInfo.z()));
      this.flags.add(stardustInfo.flag());
    }
  }
  @EventListener
  public void init(final GameLoadedEvent event) throws URISyntaxException {
    this.stardustIndicator = new AnimatedSprite(24);
    this.stardustIndicator.load("sparkle");
  }
  @EventListener
  public void render(final RenderEvent event) {
    if(engineState_8004dd20 == EngineStateEnum.SUBMAP_05) {
      this.transforms.forEach(transform -> {
        final Vector2f screenSpaceTransforms = new Vector2f();

        transformWorldspaceToScreenspace(transform, screenSpaceTransforms);

        this.cacheMV.scaling(12.0f);
        cacheMV.transfer.set(screenSpaceTransforms.x, screenSpaceTransforms.y, 0.0f);

        this.stardustIndicator.render(cacheMV);
      });
    }
  }
  private final Map<Integer, StardustInfoStruct[]> stardustData = Map.ofEntries(
          Map.entry(12, new StardustInfoStruct[] { new StardustInfoStruct(0x1c3,0xfffffe4c,0x0,0x310) }),
          Map.entry(68, new StardustInfoStruct[] { new StardustInfoStruct(0x1c4,0xfffffffe,0x6,0x3c) }),
          Map.entry(75, new StardustInfoStruct[] { new StardustInfoStruct(0x1c5,0xfffffe6c,0x1c,0xa3) }),
          Map.entry(81, new StardustInfoStruct[] { new StardustInfoStruct(0x1c6,0x48,0xffffffcd,0x123) }),
          Map.entry(83, new StardustInfoStruct[] { new StardustInfoStruct(0x1c7,0xffffffb6,0x6,0) }), // Z CHANGED FROM 0xffffff87
          Map.entry(90, new StardustInfoStruct[] { new StardustInfoStruct(0x1c8,0xa4,0x0,0x1bb) }),
          Map.entry(92, new StardustInfoStruct[] { new StardustInfoStruct(0x1c9,0xfffffea9,0xffffffa4,0x133) }),
          Map.entry(98, new StardustInfoStruct[] { new StardustInfoStruct(0x1ca,0x48,0xfffffff9,0x68) }),
          Map.entry(102, new StardustInfoStruct[] { new StardustInfoStruct(0x1cb,0xfffffeac,0x12,0x28) }),
          Map.entry(109, new StardustInfoStruct[] { new StardustInfoStruct(0x1cc,0xffffff1d,0xffffffc2,0x1b) }),
          Map.entry(145, new StardustInfoStruct[] { new StardustInfoStruct(0x1cd,0xffffffcb,0xffffffc7,0xffffffa8) }),
          Map.entry(148, new StardustInfoStruct[] { new StardustInfoStruct(0x1ce,0xffffff4b,0xfffffe6a,0x82) }),
          Map.entry(140, new StardustInfoStruct[] { new StardustInfoStruct(0x1cf,0xffffff32,0x9,0xfffffff8) }),
          Map.entry(646, new StardustInfoStruct[] { new StardustInfoStruct(0x1d0,0xffffff8b,0x9,0xfffffeb7) }),
          Map.entry(172, new StardustInfoStruct[] { new StardustInfoStruct(0x1d1,0xbd,0x0,0x22) }),
          Map.entry(173, new StardustInfoStruct[] { new StardustInfoStruct(0x1d2,0xffffffce,0xfffffee2,0xf) }),
          Map.entry(175, new StardustInfoStruct[] { new StardustInfoStruct(0x1d3,0xfffffd68,0xfffffff0,0x1a5) }),
          Map.entry(179, new StardustInfoStruct[] { new StardustInfoStruct(0x1d4,0x49,0xffffffc3,0xf7) }),
          Map.entry(180, new StardustInfoStruct[] { new StardustInfoStruct(0x1d5,0xfffffec7,0x0,0x1e7), new StardustInfoStruct(0x1d6,0xffffff4d,0x0,0xffffffec) }),
          Map.entry(201, new StardustInfoStruct[] { new StardustInfoStruct(0x1d7,0xffffff0a,0xffffff84,0xffffff32) }),
          Map.entry(214, new StardustInfoStruct[] { new StardustInfoStruct(0x1d8,0xffffffb3,0x0,0x63) }),
          Map.entry(211, new StardustInfoStruct[] { new StardustInfoStruct(0x1d9,0xffffffa0,0x0,0xffffff54) }),
          Map.entry(219, new StardustInfoStruct[] { new StardustInfoStruct(0x1da,0xd3,0x0,0xffffff2f), new StardustInfoStruct(0x1da,0x87,0x0,0xffffff43) }),
          Map.entry(204, new StardustInfoStruct[] { new StardustInfoStruct(0x1db,0xffffffec,0x0,0xffffff14) }),
          Map.entry(221, new StardustInfoStruct[] { new StardustInfoStruct(0x1dc,0x140,0xffffffce,0xfb) }),
          Map.entry(665, new StardustInfoStruct[] { new StardustInfoStruct(0x1dd,0x24e,0x55a,0xffffffa8), new StardustInfoStruct(0x1dd,0x253,0x55a,0x28) }),
          Map.entry(239, new StardustInfoStruct[] { new StardustInfoStruct(0x1de,0xffffffb8,0x0,0xffffff07) }),
          Map.entry(248, new StardustInfoStruct[] { new StardustInfoStruct(0x1df,0xffffff25,0x0,0x17b) }),
          Map.entry(265, new StardustInfoStruct[] { new StardustInfoStruct(0x1e0,0x11,0x2c,0x22d) }),
          Map.entry(277, new StardustInfoStruct[] { new StardustInfoStruct(0x1e1,0xf5,0x0,0xfffffe87) }),
          Map.entry(284, new StardustInfoStruct[] { new StardustInfoStruct(0x1e2,0xffffffc4,0x0,0xfffffeb6) }),
          Map.entry(298, new StardustInfoStruct[] { new StardustInfoStruct(0x1e3,0x96,0xd,0x50) }),
          Map.entry(316, new StardustInfoStruct[] { new StardustInfoStruct(0x1e4,0x59,0x0,0xa) }),
          Map.entry(319, new StardustInfoStruct[] { new StardustInfoStruct(0x1e5,0xaa,0x0,0x4a) }),
          Map.entry(331, new StardustInfoStruct[] { new StardustInfoStruct(0x1e6,0xffffffc0,0xffffff93,0xed) }),
          Map.entry(337, new StardustInfoStruct[] { new StardustInfoStruct(0x1e7,0x6b,0x50,0xfffffdaf) }),
          Map.entry(349, new StardustInfoStruct[] { new StardustInfoStruct(0x1e8,0x81,0x0,0xd4), new StardustInfoStruct(0x1e9,0x5b,0x0,0x1a3) }),
          Map.entry(356, new StardustInfoStruct[] { new StardustInfoStruct(0x1ea,0xffffffde,0x0,0x6d) }),
          Map.entry(374, new StardustInfoStruct[] { new StardustInfoStruct(0x1eb,0xffffff16,0xffffff82,0x1f4) }),
          Map.entry(375, new StardustInfoStruct[] { new StardustInfoStruct(0x1ec,0xad,0x0,0x118) }),
          Map.entry(379, new StardustInfoStruct[] { new StardustInfoStruct(0x1ed,0xf,0x0,0xb5) }),
          Map.entry(385, new StardustInfoStruct[] { new StardustInfoStruct(0x1ee,0xdd,0xffffffec,0x195) }),
          Map.entry(386, new StardustInfoStruct[] { new StardustInfoStruct(0x1ef,0x8e,0x313,0xffffffd7) }),
          Map.entry(490, new StardustInfoStruct[] { new StardustInfoStruct(0x1f0,0x0,0xffffffe6,0xe6) }),
          Map.entry(522, new StardustInfoStruct[] { new StardustInfoStruct(0x1f1,0x0,0x0,0x0), new StardustInfoStruct(0x1f1,0x0,0xffffffce,0x0) }),
          Map.entry(518, new StardustInfoStruct[] { new StardustInfoStruct(0x1f2,0x0,0x0,0x0), new StardustInfoStruct(0x1f2,0x0,0xffffffe2,0x0) }),
          Map.entry(515, new StardustInfoStruct[] { new StardustInfoStruct(0x1f3,0xfffffff7,0x50,0xde), new StardustInfoStruct(0x1f3,0x4f,0x50,0x82) }),
          Map.entry(566, new StardustInfoStruct[] { new StardustInfoStruct(0x1f4,0x65,0x3b,0x8) })
          );
}