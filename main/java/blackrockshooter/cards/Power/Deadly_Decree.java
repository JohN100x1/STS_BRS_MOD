package blackrockshooter.cards.Power;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.AbstractDynamicCard;
import blackrockshooter.characters.BlackRockShooter;
import blackrockshooter.powers.Deadly_Decree_pw;
import blackrockshooter.powers.Keep_Firing_pw;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class Deadly_Decree extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * (Retain.) Evoking Skulls Apply Poison equal to the enemy's current amount instead.
     */


    // TEXT DECLARATION

    public static final String ID = BlackRockShooterMod.makeID(Deadly_Decree.class.getSimpleName());
    public static final String IMG = makeCardPath("Deadly_Decree.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = 2;

    // Hey want a second magic/damage/block/unique number??? Great!
    // Go check out DefaultAttackWithVariable and theDefault.variable.DefaultCustomVariable
    // that's how you get your own custom variable that you can use for anything you like.
    // Feel free to explore other mods to see what variables they personally have and create your own ones.

    // /STAT DECLARATION/


    public Deadly_Decree() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean powerExists = false;
        Iterator var4 = p.powers.iterator();

        while(var4.hasNext()) {
            AbstractPower pow = (AbstractPower)var4.next();
            if (pow.ID.equals(Deadly_Decree_pw.POWER_ID)) {
                powerExists = true;
                break;
            }
        }

        if (!powerExists) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Deadly_Decree_pw(p)));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.selfRetain = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
