package blackrockshooter.relics;

import basemod.abstracts.CustomRelic;
import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.actions.Bleed_Random_Enemy_act;
import blackrockshooter.cards.Temp.Auto_Attack;
import blackrockshooter.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.Iterator;

import static blackrockshooter.BlackRockShooterMod.makeRelicOutlinePath;
import static blackrockshooter.BlackRockShooterMod.makeRelicPath;

public class Bloody_Water extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each combat, apply #b1 #yBleed to a random enemy.
     */

    // ID, images, text.
    public static final String ID = BlackRockShooterMod.makeID("Bloody_Water");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Bloody_Water.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Bloody_Water.png"));

    public Bloody_Water() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new Bleed_Random_Enemy_act(1));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
