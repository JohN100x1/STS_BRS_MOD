package blackrockshooter.cards.Power;

import blackrockshooter.BlackRockShooterMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import blackrockshooter.cards.AbstractDynamicCard;
import blackrockshooter.characters.BlackRockShooter;
import blackrockshooter.powers.Blue_Aura_pw;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class Blue_Aura extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Whenever you are attacked, deals 2(3) damage back and gain 1 strength.
     */


    // TEXT DECLARATION

    public static final String ID = BlackRockShooterMod.makeID(Blue_Aura.class.getSimpleName());
    public static final String IMG = makeCardPath("Blue_Aura.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = 2;
    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    // Hey want a second magic/damage/block/unique number??? Great!
    // Go check out DefaultAttackWithVariable and theDefault.variable.DefaultCustomVariable
    // that's how you get your own custom variable that you can use for anything you like.
    // Feel free to explore other mods to see what variables they personally have and create your own ones.

    // /STAT DECLARATION/


    public Blue_Aura() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new Blue_Aura_pw(p, p, magicNumber), magicNumber));
        /*
        Hey do you see this "amount" and "stackAmount" up here^ (press ctrl+p inside the parentheses to see parameters)
        THIS DOES NOT MEAN APPLY 1 POWER 1 TIMES. If you put 2 in both numbers it would apply 2. NOT "2 STACKS, 2 TIMES".

        The stackAmount is for telling ApplyPowerAction what to do if a stack already exists. Which means that it will go
        "ah, I see this power has an ID ("") that matches the power I received. I will therefore instead add the stackAmount value
        to this existing power's amount" (Thank you Johnny)

        Which is why if we want to apply 2 stacks with this card every time, want 2 in both numbers -
        "Always add 2, even if the player already has this power."
        */
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