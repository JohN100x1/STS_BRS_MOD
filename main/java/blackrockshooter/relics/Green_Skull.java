package blackrockshooter.relics;

import basemod.abstracts.CustomRelic;
import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.actions.Bleed_Random_Enemy_act;
import blackrockshooter.orbs.Skulls_orb;
import blackrockshooter.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;

import static blackrockshooter.BlackRockShooterMod.makeRelicOutlinePath;
import static blackrockshooter.BlackRockShooterMod.makeRelicPath;

public class Green_Skull extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each combat, channel #b1 #ySkulls.
     */

    // ID, images, text.
    public static final String ID = BlackRockShooterMod.makeID("Green_Skull");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Green_Skull.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Green_Skull.png"));

    public Green_Skull() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new ChannelAction(new Skulls_orb()));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
