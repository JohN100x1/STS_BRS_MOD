package blackrockshooter.relics;

import basemod.abstracts.CustomRelic;
import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.Temp.Auto_Attack;
import blackrockshooter.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static blackrockshooter.BlackRockShooterMod.makeRelicOutlinePath;
import static blackrockshooter.BlackRockShooterMod.makeRelicPath;

public class Rock_Cannon extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Whenever you play 4 attacks, add an #yAuto #yAttack to your hand.
     */

    // ID, images, text.
    public static final String ID = BlackRockShooterMod.makeID("Rock_Cannon");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Rock_Cannon.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Rock_Cannon.png"));

    private static final int COUNT = 4;

    public Rock_Cannon() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
        this.counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK){
            ++this.counter;
        }
        if (this.counter == COUNT) {
            this.counter = 0;
            this.flash();
            this.pulse = false;
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new MakeTempCardInHandAction(new Auto_Attack(), 1, false));
        } else if (this.counter == COUNT-1) {
            this.beginPulse();
            this.pulse = true;
        }
    }

    @Override
    public void atBattleStart() {
        if (this.counter == COUNT-1) {
            this.beginPulse();
            this.pulse = true;
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + COUNT + DESCRIPTIONS[1];
    }

}
