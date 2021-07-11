package blackrockshooter.relics;

import basemod.abstracts.CustomRelic;
import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.orbs.Skulls_orb;
import blackrockshooter.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static blackrockshooter.BlackRockShooterMod.makeRelicOutlinePath;
import static blackrockshooter.BlackRockShooterMod.makeRelicPath;

public class Green_Flame extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Replaces Blue Flame. The first time you exhaust a card each turn, channel 1 Skull.
     */

    // ID, images, text.
    public static final String ID = BlackRockShooterMod.makeID("Green_Flame");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Green_Flame.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Green_Flame.png"));

    private boolean triggeredThisTurn = false;

    public Green_Flame() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        triggeredThisTurn = false;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (!triggeredThisTurn) {
            triggeredThisTurn = true;
            AbstractPlayer p = AbstractDungeon.player;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(p, this));
            this.addToBot(new ChannelAction(new Skulls_orb()));
        }
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(Blue_Flame.ID)) {
            for(int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if ((AbstractDungeon.player.relics.get(i)).relicId.equals(Blue_Flame.ID)) {
                    this.instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r.relicId.equals(ID)) {
                    r.flash();
                }
            }
        } else {
            super.obtain();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
