package blackrockshooter.cards.Skill;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.AbstractDefaultCard;
import blackrockshooter.characters.BlackRockShooter;
import blackrockshooter.powers.Sleep_pw;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class Rest extends AbstractDefaultCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Heal 20 of max HP. Remove all your Debuffs. Exit Your Stance. Gain 3(2) sleep. End your turn. Exhaust.
     */


    // TEXT DECLARATION

    public static final String ID = BlackRockShooterMod.makeID(Rest.class.getSimpleName());
    public static final String IMG = makeCardPath("Rest.png");


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = 3;
    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = -1;


    // /STAT DECLARATION/


    public Rest() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, 20));
        AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(p));
        AbstractDungeon.actionManager.addToBottom(new NotStanceCheckAction("Neutral", new VFXAction(new EmptyStanceEffect(p.hb.cX, p.hb.cY), 0.1F)));
        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Sleep_pw(p, p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new PressEndTurnButtonAction());
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}