package blackrockshooter.cards.Attack;

import blackrockshooter.actions.Madness_Rain_act;
import blackrockshooter.cards.AbstractDynamicCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.characters.BlackRockShooter;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class Madness_Rain extends AbstractDynamicCard {

    public static final String ID = BlackRockShooterMod.makeID(Madness_Rain.class.getSimpleName());
    public static final String IMG = makeCardPath("Madness_Rain.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = -1;

    private static final int DAMAGE = 1;
    private static final int UPGRADE_PLUS_DMG = 1;

    // /STAT DECLARATION/


    public Madness_Rain() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new Madness_Rain_act(p, this.multiDamage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

}