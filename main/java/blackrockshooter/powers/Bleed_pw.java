package blackrockshooter.powers;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.WaterDropEffect;

import static blackrockshooter.BlackRockShooterMod.makePowerPath;

public class Bleed_pw extends AbstractPower implements HealthBarRenderPower {
    public AbstractCreature source;

    public static final String POWER_ID = BlackRockShooterMod.makeID("Bleed_pw");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Bleed84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Bleed32.png"));

    public static final float bleedPercentage = 0.33333334F;

    public Bleed_pw(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        if (this.amount >= 9999) {
            this.amount = 9999;
        }

        type = PowerType.DEBUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public int getHealthBarAmount() {
        return (int)(bleedPercentage * owner.maxHealth);
    }
    @Override
    public Color getColor() {
        return new Color(0.6F, 0F, 0F, 1.0F);
    }

    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("BLOOD_SPLAT", 0.05F);
    }

    @Override
    public void atStartOfTurn() {
        increaseBleedAmount();
        activateBleedDamage();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.type == DamageInfo.DamageType.NORMAL) {
            increaseBleedAmount();
            activateBleedDamage();
        }
        return damageAmount;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        activateBleedDamage();
    }

    @Override
    public void onInitialApplication() {
        activateBleedDamage();
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (owner != null && !owner.isPlayer) {
            description = DESCRIPTIONS[1] + bleedIncrement() + DESCRIPTIONS[3] + 10 + DESCRIPTIONS[5];
        } else {
            description = DESCRIPTIONS[0] + bleedIncrement() + DESCRIPTIONS[2] + 10 + DESCRIPTIONS[4];
        }
    }

    public int bleedIncrement() {
        if (AbstractDungeon.player.hasRelic("blackrockshooter:King_Saw")){
            return 2;
        } else {
            return 1;
        }
    }

    public void increaseBleedAmount() {
        this.flash();
        CardCrawlGame.sound.playA("BLOOD_SPLAT", 0F);
        AbstractDungeon.effectsQueue.add(new WaterDropEffect(owner.hb.cX, owner.hb.cY + 250.0F * Settings.scale));
        if (bleedIncrement() == 2){
            ++amount;
        }
        ++amount;
    }

    public void activateBleedDamage() {
        if (amount >= 10) {
            CardCrawlGame.sound.playA("BLOOD_SWISH", 0F);
            int lossAmount = (int)(bleedPercentage * owner.maxHealth);
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(owner.hb.cX, owner.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToTop(new LoseHPAction(owner, source, lossAmount));
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
    }

    public AbstractPower makeCopy() {
        return new Bleed_pw(owner, source, amount);
    }
}