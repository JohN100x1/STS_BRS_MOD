package blackrockshooter.relics;

import basemod.abstracts.CustomRelic;
import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static blackrockshooter.BlackRockShooterMod.makeRelicOutlinePath;
import static blackrockshooter.BlackRockShooterMod.makeRelicPath;

public class Blue_Flame extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * The first time you exhaust a card each combat, gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = BlackRockShooterMod.makeID("Blue_Flame");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Blue_Flame.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Blue_Flame.png"));

    private boolean triggeredThisCombat = false;

    public Blue_Flame() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        triggeredThisCombat = false;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (!triggeredThisCombat) {
            triggeredThisCombat = true;
            flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
