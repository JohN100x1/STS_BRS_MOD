package blackrockshooter.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Aggressor_stance_change_generator extends AbstractGameEffect {
    private float x;
    private float y;

    public Aggressor_stance_change_generator(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        for(int i = 0; i < 10; ++i) {
            AbstractDungeon.effectsQueue.add(new Aggressor_stance_change_eff(this.x));
        }
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}