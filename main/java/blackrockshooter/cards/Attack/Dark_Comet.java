package blackrockshooter.cards.Attack;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.AbstractDynamicCard;
import blackrockshooter.characters.BlackRockShooter;
import blackrockshooter.powers.Bleed_pw;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

// Remove all Block from ALL enemies. Apply 2(3) Bleed to ALL enemies.

public class Dark_Comet extends AbstractDynamicCard {

    public static final String ID = BlackRockShooterMod.makeID(Dark_Comet.class.getSimpleName());
    public static final String IMG = makeCardPath("Dark_Comet.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = 2;
    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    // /STAT DECLARATION/


    public Dark_Comet() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (final AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDead && !mo.isDying) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(mo, p));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new Bleed_pw(mo, p, magicNumber), magicNumber));
                }
            }
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }

}