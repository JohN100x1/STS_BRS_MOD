package blackrockshooter.orbs;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.*;

import static blackrockshooter.BlackRockShooterMod.makeOrbPath;

public class Skulls_orb extends AbstractOrb {

    // Standard ID/Description
    public static final String ORB_ID = BlackRockShooterMod.makeID("Skulls_orb");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESC = orbString.DESCRIPTION;

    private static final Texture IMG = TextureLoader.getTexture(makeOrbPath("Skulls_orb.png"));
    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;

    public Skulls_orb() {

        ID = ORB_ID;
        name = orbString.NAME;
        img = IMG;

        evokeAmount = baseEvokeAmount = 3;
        passiveAmount = basePassiveAmount = 1;

        updateDescription();
        channelAnimTimer = 0.5f;
    }

    @Override
    public void updateDescription() { // Set the on-hover description of the orb
        applyFocus(); // Apply Focus (Look at the next method)
        if (AbstractDungeon.player.hasPower("blackrockshooter:Deadly_Decree_pw")) {
            description = DESC[0] + passiveAmount + DESC[3];
        } else {
            description = DESC[0] + passiveAmount + DESC[1] + evokeAmount + DESC[2];
        }
    }

    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_POISON2"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), 0.1f));
        if (AbstractDungeon.player.hasPower("blackrockshooter:Deadly_Decree_pw")) {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (!m.isDead && !m.isDying && m.hasPower("Poison")) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new PoisonPower(m, AbstractDungeon.player, m.getPower("Poison").amount), m.getPower("Poison").amount));
                    }
                }
            }
        } else {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (!m.isDead && !m.isDying) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new PoisonPower(m, AbstractDungeon.player, evokeAmount), evokeAmount));
                    }
                }
            }
        }
    }

    @Override
    public void onEndOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_POISON2"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), 0.1f));
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDead && !m.isDying) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new PoisonPower(m, AbstractDungeon.player, passiveAmount), passiveAmount));
                }
            }
        }
    }

    @Override
    public void updateAnimation() {// You can totally leave this as is.
        // If you want to create a whole new orb effect - take a look at conspire's Water Orb. It includes a custom sound, too!
        super.updateAnimation();
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if (vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(cX, cY));
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
    }

    // Render the orb.
    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, c.a / 1.0f));
        sb.draw(img, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale, scale, 0, 0, 0, 96, 96, false, false);
        renderText(sb);
        hb.render(sb);
    }


    @Override
    public void triggerEvokeAnimation() { // The evoke animation of this orb is the dark-orb activation effect.
        AbstractDungeon.effectsQueue.add(new LightningOrbActivateEffect(cX, cY));
    }

    @Override
    public void playChannelSFX() { // When you channel this orb, the ATTACK_FIRE effect plays ("Fwoom").
        CardCrawlGame.sound.play("ATTACK_POISON", 0.1f);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new Skulls_orb();
    }
}
