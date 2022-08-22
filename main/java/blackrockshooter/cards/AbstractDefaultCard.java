package blackrockshooter.cards;

import basemod.abstracts.CustomCard;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractDefaultCard extends CustomCard {

    // Custom Abstract Cards can be a bit confusing. While this is a simple base for simply adding a second magic number,
    // if you're new to modding I suggest you skip this file until you know what unique things that aren't provided
    // by default, that you need in your own cards.

    // In this example, we use a custom Abstract Card in order to define a new magic number. From here on out, we can
    // simply use that in our cards, so long as we put "extends AbstractDynamicCard" instead of "extends CustomCard" at the start.
    // In simple terms, it's for things that we don't want to define again and again in every single card we make.

    public int counterNumber;        // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int baseCounterNumber;    // And our base stat - the number in it's base state. It will reset to that by default.
    public boolean upgradedCounterNumber; // A boolean to check whether the number has been upgraded or not.
    public boolean isCounterNumberModified; // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)

    public AbstractDefaultCard(final String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

        // Set all the things to their default values.
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isCounterNumberModified = false;
    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
        if (upgradedCounterNumber) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            counterNumber = baseCounterNumber; // Show how the number changes, as out of combat, the base number of a card is shown.
            isCounterNumberModified = true; // Modified = true, color it green to highlight that the number is being changed.
        }

    }

    public void upgradeCounterNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        baseCounterNumber += amount; // Upgrade the number by the amount you provide in your card.
        counterNumber = baseCounterNumber; // Set the number to be equal to the base value.
        upgradedCounterNumber = true; // Upgraded = true - which does what the above method does.
    }
}