package blackrockshooter.relics;

import basemod.abstracts.CustomRelic;
import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.actions.Bleed_Random_Enemy_act;
import blackrockshooter.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

import static blackrockshooter.BlackRockShooterMod.makeRelicOutlinePath;
import static blackrockshooter.BlackRockShooterMod.makeRelicPath;

public class Rock_Cannon extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Your Auto Attacks have Retain.
     */

    // ID, images, text.
    public static final String ID = BlackRockShooterMod.makeID("Rock_Cannon");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Rock_Cannon.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Rock_Cannon.png"));

    public Rock_Cannon() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
