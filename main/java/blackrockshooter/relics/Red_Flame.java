package blackrockshooter.relics;

import basemod.abstracts.CustomRelic;
import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static blackrockshooter.BlackRockShooterMod.makeRelicOutlinePath;
import static blackrockshooter.BlackRockShooterMod.makeRelicPath;

public class Red_Flame extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Replaces Blue Flame. The first time you exhaust a card each turn, gain 1 Strength.
     */

    // ID, images, text.
    public static final String ID = BlackRockShooterMod.makeID("Red_Flame");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Red_Flame.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Red_Flame.png"));

    private boolean triggeredThisTurn = false;

    public Red_Flame() {
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
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
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
