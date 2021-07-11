package blackrockshooter.powers;

import basemod.interfaces.CloneablePowerInterface;
import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.Temp.Auto_Attack;
import blackrockshooter.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static blackrockshooter.BlackRockShooterMod.makePowerPath;

public class Rock_Cannon_Mk2_pw extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = BlackRockShooterMod.makeID("Rock_Cannon_Mk2_pw");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Rock_Cannon_Mk284.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Rock_Cannon_Mk232.png"));

    public Rock_Cannon_Mk2_pw(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
        updateExistingKeepFiring();
    }

    private void updateExistingKeepFiring() {
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof Auto_Attack) {
                if (!c.upgraded) {
                    c.magicNumber = c.baseMagicNumber = 2 + amount;
                } else {
                    c.magicNumber = c.baseMagicNumber = 3 + amount;
                }
            }
        }
        for (final AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof Auto_Attack) {
                if (!c.upgraded) {
                    c.magicNumber = c.baseMagicNumber = 2 + amount;
                } else {
                    c.magicNumber = c.baseMagicNumber = 3 + amount;
                }
            }
        }
        for (final AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof Auto_Attack) {
                if (!c.upgraded) {
                    c.magicNumber = c.baseMagicNumber = 2 + amount;
                } else {
                    c.magicNumber = c.baseMagicNumber = 3 + amount;
                }
            }
        }
        for (final AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof Auto_Attack) {
                if (!c.upgraded) {
                    c.magicNumber = c.baseMagicNumber = 2 + amount;
                } else {
                    c.magicNumber = c.baseMagicNumber = 3 + amount;
                }
            }
        }
    }

    public void onDrawOrDiscard() {
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof Auto_Attack) {
                if (!c.upgraded) {
                    c.magicNumber = c.baseMagicNumber = 2 + amount;
                } else {
                    c.magicNumber = c.baseMagicNumber = 3 + amount;
                }
            }
        }
    }

    // Note: If you want to apply an effect when a power is being applied you have 3 options:
    //onInitialApplication is "When THIS power is first applied for the very first time only."
    //onApplyPower is "When the owner applies a power to something else (only used by Sadistic Nature)."
    //onReceivePowerPower from StSlib is "When any (including this) power is applied to the owner."


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new Rock_Cannon_Mk2_pw(owner, source, amount);
    }
}