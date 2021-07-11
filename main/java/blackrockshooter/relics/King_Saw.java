package blackrockshooter.relics;

import basemod.abstracts.CustomRelic;
import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

import static blackrockshooter.BlackRockShooterMod.makeRelicOutlinePath;
import static blackrockshooter.BlackRockShooterMod.makeRelicPath;

public class King_Saw extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * All Bleed increase by 2 instead of 1.
     */

    // ID, images, text.
    public static final String ID = BlackRockShooterMod.makeID("King_Saw");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("King_Saw.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("King_Saw.png"));

    public King_Saw() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
