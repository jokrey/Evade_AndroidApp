package evade.game;

import util.animation.engine.AnimationEngine;
import util.animation.pipeline.AnimationDrawer;
import util.animation.pipeline.AnimationPipeline;
import util.animation.util.AEColor;
import util.animation.util.AEPoint;
import util.animation.util.AERect;

public class EvadeGamePipeline extends AnimationPipeline {
    public EvadeGamePipeline(AnimationDrawer drawer) {
        super(drawer);
    }

    //to disable dragging the screen...
    @Override public boolean useUserMidOV() { return false; }

    @Override protected void drawBackground(AERect drawBounds, AnimationEngine engine_uncast) {
        super.drawBackground(drawBounds, engine_uncast);
        
        EvadeGameEngine engine = (EvadeGameEngine) engine_uncast;

        if (engine.game_points > 0.0d) {
            String gamePointsAsString = ((int) engine.game_points) + "";
            getDrawer().drawString(engine.game_points_boost_active ? new AEColor(255, 66, 44, 44) : new AEColor(255, 44, 44, 44), 333, gamePointsAsString, drawBounds.getWidth()/2, drawBounds.getHeight()/2);
        } else {
            int i;
            getDrawer().drawString(AEColor.WHITE, "HELP", new AERect((drawBounds.x + drawBounds.getWidth())-drawBounds.getWidth()/10, 0, drawBounds.getWidth()/10, drawBounds.getHeight()/10));

            int spawnAmountSettingsBar = (int) (drawBounds.getHeight() * 0.15d);
            //p.setShader(new LinearGradient(0.0f, 0.0f, (float) getWidth(), 0.0f, ViewCompat.MEASURED_STATE_MASK, Color.rgb(150, 0, 0), TileMode.MIRROR));
            getDrawer().fillRect(new AEColor(255, 100,0,0), new AERect(0, drawBounds.getHeight() - spawnAmountSettingsBar, drawBounds.getWidth(), spawnAmountSettingsBar));
            int avsps_x = (int) (((engine.average_spawns_per_second - EvadeGameEngine.min_av_sps) / (EvadeGameEngine.max_av_sps - EvadeGameEngine.min_av_sps)) * drawBounds.getWidth());
            getDrawer().fillRect(new AEColor(255, 0, 0, 150), new AERect(avsps_x - drawBounds.getWidth() / 200, drawBounds.getHeight() - spawnAmountSettingsBar, drawBounds.getWidth() / 100, spawnAmountSettingsBar));
            getDrawer().drawLine(AEColor.BLACK, new AEPoint(drawBounds.getWidth()*(0.15), drawBounds.getHeight() - spawnAmountSettingsBar/2), new AEPoint(drawBounds.getWidth()*(0.85), drawBounds.getHeight() - spawnAmountSettingsBar/2));
            getDrawer().drawLine(AEColor.BLACK, new AEPoint(drawBounds.getWidth()*(0.85), drawBounds.getHeight() - spawnAmountSettingsBar/2), new AEPoint(drawBounds.getWidth()*(0.85) - spawnAmountSettingsBar, drawBounds.getHeight()));
            getDrawer().drawLine(AEColor.BLACK, new AEPoint(drawBounds.getWidth()*(0.85), drawBounds.getHeight() - spawnAmountSettingsBar/2), new AEPoint(drawBounds.getWidth()*(0.85) - spawnAmountSettingsBar, drawBounds.getHeight()-spawnAmountSettingsBar));

            if (engine.lastScores.isEmpty()) {
                getDrawer().drawString(AEColor.DARK_GRAY, 155, "-", drawBounds.getWidth() / 2 - drawBounds.getWidth() / 6, drawBounds.getHeight() / 3);
            } else {
                for (i = engine.lastScores.size() - 1; i >= 1; i--) {
                    ScoreSet lastScore = engine.lastScores.get(i);
                    getDrawer().drawString(lastScore.getColor(), 155 - (i * 15) < 0 ? 0.0f : 155 - (i * 15), lastScore.getPoints()+"", drawBounds.getWidth() / 2 - drawBounds.getWidth() / 6 - (drawBounds.getWidth() / 8) - (drawBounds.getHeight() / 25) * i, drawBounds.getHeight() / 3 - (drawBounds.getHeight() / 25) * i);
                }
                ScoreSet lastScore = engine.lastScores.get(0);
                getDrawer().drawString(lastScore.getColor(), 155, lastScore.getPoints()+"", drawBounds.getWidth() / 2 - drawBounds.getWidth() / 6, drawBounds.getHeight() / 3);
            }
            if (engine.highScores.isEmpty()) {
                getDrawer().drawString(AEColor.DARK_GRAY, 155, "-", drawBounds.getWidth() / 2 + drawBounds.getWidth() / 6, drawBounds.getHeight() / 3);
            } else {
                for (i = engine.highScores.size() - 1; i >= 1; i--) {
                    ScoreSet highscore = engine.highScores.get(i);
                    getDrawer().drawString(highscore.getColor(), 155 - (i * 15) < 0 ? 0.0f : 155 - (i * 15), highscore.getPoints()+"", drawBounds.getWidth() / 2 + drawBounds.getWidth() / 6 + (drawBounds.getWidth() / 8) + (drawBounds.getHeight() / 25) * i, drawBounds.getHeight() / 3 - (drawBounds.getHeight() / 25) * i);
                }
                ScoreSet greatestHighscore = engine.highScores.get(0);
                getDrawer().drawString(greatestHighscore.getColor(), 155, greatestHighscore.getPoints()+"", drawBounds.getWidth() / 2 + drawBounds.getWidth() / 6, drawBounds.getHeight() / 3);
            }
        }
    }

    @Override protected void drawForeground(AERect drawBounds, AnimationEngine engine_uncast) {
        super.drawForeground(drawBounds, engine_uncast);

        EvadeGameEngine engine = (EvadeGameEngine) engine_uncast;

        if (engine.showingHelpScreen && engine.game_points <= 0) {
            getDrawer().drawString(AEColor.WHITE, 33, "press anywhere to play", drawBounds.getWidth() / 2, drawBounds.getHeight() / 8);
            getDrawer().drawString(AEColor.WHITE, 26, "SLIDE FOR MORE POINTS - (and more stuff)", drawBounds.getWidth() / 2, drawBounds.getHeight() * 0.85d + ((drawBounds.getHeight() * 0.15d) / 2.0d));
            getDrawer().drawString(AEColor.WHITE, 26, "last scores", drawBounds.getWidth() / 2 - (drawBounds.getWidth() / 6), drawBounds.getHeight() / 3 + drawBounds.getHeight() / 10);
            getDrawer().drawString(AEColor.WHITE, 26, "high scores", drawBounds.getWidth() / 2 + (drawBounds.getWidth() / 6), drawBounds.getHeight() / 3 + drawBounds.getHeight() / 10);
            getDrawer().drawString(AEColor.WHITE, 24, "How to play?", drawBounds.getWidth() / 2, drawBounds.getHeight() * 0.64d);
            getDrawer().drawString(AEColor.WHITE, 22, "where your finger goes  the ball goes", drawBounds.getWidth() / 2, drawBounds.getHeight() * 0.64d+24);
            getDrawer().drawString(AEColor.WHITE, 24, "How to score?", drawBounds.getWidth() / 2, drawBounds.getHeight() * 0.78d);
            getDrawer().drawString(AEColor.WHITE, 22, "simply evade stuff", drawBounds.getWidth() / 2, drawBounds.getHeight() * 0.78d+24);
        }
    }
}
