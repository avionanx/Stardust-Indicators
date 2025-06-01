package lod.stardustindicators;

import legend.core.QueuedModelStandard;
import legend.core.gpu.Bpp;
import legend.core.gte.MV;
import legend.core.opengl.MeshObj;
import legend.core.opengl.QuadBuilder;
import legend.core.opengl.Texture;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static legend.core.GameEngine.*;
import static lod.stardustindicators.Main.MOD_ID;

public class AnimatedSprite {

    private MeshObj quad;
    ArrayList<Texture> textures;

    private int currentFrame = 0;
    final private int maxFrames;

    AnimatedSprite(int maxFrames) {
        this.textures = new ArrayList<>();
        this.maxFrames = maxFrames;
    }

    public void load(String name) throws URISyntaxException {
        int index = 0;
        while(true) { // oops i sinned again
            final Path path = Path.of("mods", "stardustindicators", "%s".formatted(name), "%d.png".formatted(index));
            if(Files.exists(path)) {
                this.textures.add(Texture.png(path));
                index++;
            }else {
                break;
            }
        }

        this.quad = new QuadBuilder(MOD_ID)
                .uvSize(1.0f,1.0f)
                .bpp(Bpp.BITS_24)
                .size(1.0f,1.0f)
                .pos(-0.5f,-0.5f,0.0f)
                .rgb(1.0f, 1.0f, 1.0f)
                .build();
    }

    public void render(final MV screenSpaceTransforms) {
        final double progress = (double)currentFrame/maxFrames;
        final Texture currentTexture = this.textures.get((int)Math.floor(progress * (this.textures.size() - 1)));

        RENDERER.queueOrthoModel(this.quad, screenSpaceTransforms, QueuedModelStandard.class)
                .screenspaceOffset(GPU.getOffsetX() + GTE.getScreenOffsetX(), GPU.getOffsetY() + GTE.getScreenOffsetY())
                .useTextureAlpha()
                .texture(currentTexture);

        currentFrame = (currentFrame + 1) % maxFrames;
    }

    public void unload() {
        this.textures.forEach(Texture::delete);
        this.quad.delete();
    }
}
