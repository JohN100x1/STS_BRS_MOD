package blackrockshooter.cards.Attack;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.actions.Execute_act;
import blackrockshooter.cards.AbstractDynamicCard;
import blackrockshooter.characters.BlackRockShooter;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class Execute extends AbstractDynamicCard {

    // Deal 10% of max HP. If Fatal, permanently increase this card's damage by 2(3)%. Exhaust.

    public static final String ID = BlackRockShooterMod.makeID(Execute.class.getSimpleName());
    public static final String IMG = makeCardPath("Execute.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = 2;
    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    // /STAT DECLARATION/


    public Execute() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        misc = 10;
        baseDamage = misc;
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new Execute_act(m, damage, magicNumber, uuid));
    }

    @Override
    public void applyPowers() {
        this.baseBlock = this.misc;
        super.applyPowers();
        StringBuilder damages = new StringBuilder();
        if (AbstractDungeon.getMonsters().monsters.size() == 1) {
            damages.append((int) ((misc/100F) * AbstractDungeon.getMonsters().monsters.get(0).maxHealth));
        } else {
            Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();

            while(var1.hasNext()) {
                AbstractMonster m = (AbstractMonster)var1.next();
                if (!m.isDead && !m.isDying) {
                    damages.append((int) ((misc/100F) * AbstractDungeon.getMonsters().monsters.get(0).maxHealth));
                    if (var1.hasNext()) {
                        damages.append(", ");
                    }
                }
            }
        }

        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + damages;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];

        this.initializeDescription();
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