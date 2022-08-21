package blackrockshooter.powers;

import basemod.interfaces.CloneablePowerInterface;
import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

import static blackrockshooter.BlackRockShooterMod.makePowerPath;

public class TreasureHunt_pw extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = BlackRockShooterMod.makeID("Treasure_Hunt_pw");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - a 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Treasure_Hunt84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Treasure_Hunt32.png"));

    public TreasureHunt_pw(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onDeath() {
        AbstractDungeon.player.gainGold(amount);

        for (int i = 0; i < amount; ++i) {
            AbstractDungeon.effectList.add(new GainPennyEffect(source, this.owner.hb.cX, this.owner.hb.cY, this.source.hb.cX, this.source.hb.cY, true));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new TreasureHunt_pw(owner, source, amount);
    }
}