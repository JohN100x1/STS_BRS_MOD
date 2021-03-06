package blackrockshooter.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

public class Rain_of_Madness_eff extends AbstractGameEffect {
    private float x;
    private float y;
    private static final float DUR = 1.0F;
    private static TextureAtlas.AtlasRegion img;
    private boolean playedSfx = false;
    private boolean flipHorizontal = false;

    public Rain_of_Madness_eff(float x, float y, boolean flipHorizontal) {
        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThick");
        }

        this.flipHorizontal = flipHorizontal;
        this.x = x;
        this.y = y;
        this.color = Color.MAGENTA.cpy();
        this.duration = 1.0F;
        this.startingDuration = 1.0F;
    }

    public void update() {
        if (!this.playedSfx) {
            AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.MAGENTA));
            this.playedSfx = true;
            CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT");
            CardCrawlGame.screenShake.rumble(2.0F);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, this.duration - 0.5F);
        } else {
            this.color.a = Interpolation.pow2Out.apply(0.0F, 1.0F, this.duration);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(1.0F, 0.5F, 1.0F, this.color.a));
        if (!this.flipHorizontal) {
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F + MathUtils.random(-0.05F, 0.05F), this.scale * 1.5F + MathUtils.random(-0.1F, 0.1F), MathUtils.random(-20F, 20F));
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F + MathUtils.random(-0.05F, 0.05F), this.scale * 1.5F + MathUtils.random(-0.1F, 0.1F), MathUtils.random(-20F, 20F));
            sb.setColor(this.color);
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F, this.scale / 2.0F, MathUtils.random(-10F, 10F));
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F, this.scale / 2.0F, MathUtils.random(-10F, 10F));
        } else {
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F + MathUtils.random(-0.05F, 0.05F), this.scale * 1.5F + MathUtils.random(-0.1F, 0.1F), MathUtils.random(176.25F, 198.75F));
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F + MathUtils.random(-0.05F, 0.05F), this.scale * 1.5F + MathUtils.random(-0.1F, 0.1F), MathUtils.random(176.25F, 198.75F));
            sb.setColor(this.color);
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F, this.scale / 2.0F, MathUtils.random(183.75F, 191.25F));
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F, this.scale / 2.0F, MathUtils.random(183.75F, 191.25F));
        }

        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}