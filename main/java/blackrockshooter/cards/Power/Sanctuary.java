package blackrockshooter.cards.Power;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.AbstractDynamicCard;
import blackrockshooter.characters.BlackRockShooter;
import blackrockshooter.powers.Keep_Firing_pw;
import blackrockshooter.powers.Sanctuary_pw;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class Sanctuary extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Every 3 times you gain Block, gain 1 Dexterity.
     */


    // TEXT DECLARATION

    public static final String ID = BlackRockShooterMod.makeID(Sanctuary.class.getSimpleName());
    public static final String IMG = makeCardPath("Sanctuary.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    public static final AbstractCard.CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;
    private static final int MAGIC = 1;

    // Hey want a second magic/damage/block/unique number??? Great!
    // Go check out DefaultAttackWithVariable and theDefault.variable.DefaultCustomVariable
    // that's how you get your own custom variable that you can use for anything you like.
    // Feel free to explore other mods to see what variables they personally have and create your own ones.

    // /STAT DECLARATION/


    public Sanctuary() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean powerExists = false;
        Iterator var4 = p.powers.iterator();

        while(var4.hasNext()) {
            AbstractPower pow = (AbstractPower)var4.next();
            if (pow.ID.equals(Sanctuary_pw.POWER_ID)) {
                powerExists = true;
                break;
            }
        }

        if (!powerExists) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Sanctuary_pw(p)));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}