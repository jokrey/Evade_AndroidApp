package evade.game;

import util.animation.engine.AnimationEngine;
import util.animation.engine.MovingAnimationObject;
import util.animation.pipeline.AnimationObject;
import util.animation.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvadeGameEngine extends AnimationEngine {
    public static final double max_av_sps = 10.0d;
    public static final double min_av_sps = 0.1d;
    public double average_spawns_per_second = 3.5d;
    public boolean gameOver = false;
    public double game_points = -1.0d;
    public boolean game_points_boost_active = false;
    public List<ScoreSet> highScores = new ArrayList<>();
    public List<ScoreSet> lastScores = new ArrayList<>();
    public boolean showingHelpScreen = false;

    private MovingAnimationObject gameBall;
    private AnimationObject lineToMouse;

    @Override public AESize getVirtualBoundaries() {
        return new AESize(1920, 1080);
    }
    @Override public void initiate() {
        clearObjects();
        AEColor randClr = AEColor.getRandomColor(100, 255);

        initateObjectAt(0, lineToMouse = new AnimationObject(null, AnimationObject.LINE,randClr));
        initateObjectAt(1, gameBall = new MovingAnimationObject(
                getVirtualLimit_width()/2,
                getVirtualLimit_height()/2,
                0,0,0,0,55,55,
                MovingAnimationObject.OVAL,randClr));
        this.game_points = 0.0d;
        this.gameOver = true;
        showingHelpScreen=false;
    }
    @Override public void mouseClicked(int mouseCode) {
        if (isPaused() || gameOver) {
            if (game_points <= 0.0d && (mouseP.getY() > getVirtualLimit_height() * 0.85d)) {
                double x = (double) (mouseP.getX() / ((float) getVirtualLimit_width()));
                x *= max_av_sps - min_av_sps;
                average_spawns_per_second = x + min_av_sps;
                double d = average_spawns_per_second;
                if (d < min_av_sps) {
                    average_spawns_per_second = min_av_sps;
                }
                d = average_spawns_per_second;
                if (d > max_av_sps) {
                    average_spawns_per_second = max_av_sps;
                }
            } else {
                AERect drawBounds = new AERect(0,0,getVirtualLimit_width(), getVirtualLimit_height());
                if ((mouseP.getX() > (drawBounds.x + drawBounds.getWidth())-drawBounds.getWidth()/10) && (mouseP.getY() < drawBounds.getHeight()/10)) {
                    showingHelpScreen = true;
                } else {
                    if (gameBall == null || AnimationObject.distance(gameBall.getMidAsPoint(), mouseP) >= gameBall.getW()) {
                        start();
                        gameOver=false;
                    } else {
                        lineToMouse.drawParam = AEColor.getRandomColor(100,255);
                        gameBall.drawParam = lineToMouse.drawParam;
                    }
                }
            }
        } else {
            if (gameBall != null && AnimationObject.distance(gameBall.getMidAsPoint(), mouseP) < gameBall.getW()) {
                pause();
            }
        }
    }

    private AEPoint mouseP = null;
    @Override public void locationInputChanged(AEPoint p) {
        mouseP=p;
    }



    @Override protected void calculateTick() {
        if (this.gameOver) {
            if(this.game_points!=0) {//only once called after each gameOver.
                upandvalidateScores();

                initiate();
            }
        } else {
            boolean newObstacleSpawned = false;
            if (AE_UTIL.getRandomNr(0, (int) (((double) getTicksPerSecond()) / this.average_spawns_per_second)) == 0) {
                newObstacleSpawned = true;
                int orientation = AE_UTIL.getRandomNr(0, 3);
                double diameter = AE_UTIL.getRandomNr(((double) getVirtualLimit_height()) / 33.0d, ((double) getVirtualLimit_height()) / 13.0d);
                double diameter_w = diameter;
                double diameter_h = diameter;
                double randomNr;
                double d;
                double randomNr2;
                double randomNr3;
                double randomNr4;
                double randomNr5;
                int i;
                if (orientation == 0) {
                    randomNr = AE_UTIL.getRandomNr((-diameter_w) + 1.0d, (double) (getVirtualLimit_width() - 1));
                    d = (-diameter_h) + 1.0d;
                    randomNr2 = AE_UTIL.getRandomNr(((double) (-getVirtualLimit_height())) / 55.0d, ((double) getVirtualLimit_height()) / 55.0d);
                    randomNr3 = AE_UTIL.getRandomNr(((double) getVirtualLimit_height()) / 55.0d, ((double) getVirtualLimit_height()) / 15.0d);
                    randomNr4 = AE_UTIL.getRandomNr(((double) (-getVirtualLimit_height())) / 55.0d, ((double) getVirtualLimit_height()) / 55.0d);
                    randomNr5 = AE_UTIL.getRandomNr(0.0d, ((double) getVirtualLimit_height()) / 33.0d);
                    if (AE_UTIL.getRandomNr(0, 1) == 1) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    initateNewObject(new MovingAnimationObject(randomNr, d, randomNr2, randomNr3, randomNr4, randomNr5, diameter_w, diameter_h, i, AEColor.getRandomColor(100,255)));
                } else if (orientation == 1) {
                    randomNr = (double) (getVirtualLimit_width() - 1);
                    d = AE_UTIL.getRandomNr((-diameter_h) + 1.0d, (double) (getVirtualLimit_height() - 1));
                    randomNr2 = -AE_UTIL.getRandomNr(((double) getVirtualLimit_height()) / 55.0d, ((double) getVirtualLimit_height()) / 15.0d);
                    randomNr3 = AE_UTIL.getRandomNr(((double) (-getVirtualLimit_height())) / 55.0d, ((double) getVirtualLimit_height()) / 55.0d);
                    randomNr4 = -AE_UTIL.getRandomNr(0.0d, ((double) getVirtualLimit_height()) / 33.0d);
                    randomNr5 = AE_UTIL.getRandomNr(((double) (-getVirtualLimit_height())) / 55.0d, ((double) getVirtualLimit_height()) / 55.0d);
                    if (AE_UTIL.getRandomNr(0, 1) == 1) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    initateNewObject(new MovingAnimationObject(randomNr, d, randomNr2, randomNr3, randomNr4, randomNr5, diameter_w, diameter_h, i, AEColor.getRandomColor(100,255)));
                } else if (orientation == 2) {
                    randomNr = AE_UTIL.getRandomNr((-diameter_w) + 1.0d, (double) (getVirtualLimit_width() - 1));
                    d = (double) (getVirtualLimit_height() - 1);
                    randomNr2 = AE_UTIL.getRandomNr(((double) (-getVirtualLimit_height())) / 55.0d, ((double) getVirtualLimit_height()) / 55.0d);
                    randomNr3 = -AE_UTIL.getRandomNr(((double) getVirtualLimit_height()) / 55.0d, ((double) getVirtualLimit_height()) / 15.0d);
                    randomNr4 = AE_UTIL.getRandomNr(((double) (-getVirtualLimit_height())) / 55.0d, ((double) getVirtualLimit_height()) / 55.0d);
                    randomNr5 = -AE_UTIL.getRandomNr(0.0d, ((double) getVirtualLimit_height()) / 33.0d);
                    if (AE_UTIL.getRandomNr(0, 1) == 1) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    initateNewObject(new MovingAnimationObject(randomNr, d, randomNr2, randomNr3, randomNr4, randomNr5, diameter_w, diameter_h, i, AEColor.getRandomColor(100,255)));
                } else if (orientation == 3) {
                    randomNr = (-diameter_w) + 1.0d;
                    d = AE_UTIL.getRandomNr((-diameter_h) + 1.0d, (double) (getVirtualLimit_height() - 1));
                    randomNr2 = AE_UTIL.getRandomNr(((double) getVirtualLimit_height()) / 55.0d, ((double) getVirtualLimit_height()) / 15.0d);
                    randomNr3 = AE_UTIL.getRandomNr(((double) (-getVirtualLimit_height())) / 55.0d, ((double) getVirtualLimit_height()) / 55.0d);
                    randomNr4 = AE_UTIL.getRandomNr(0.0d, ((double) getVirtualLimit_height()) / 33.0d);
                    randomNr5 = AE_UTIL.getRandomNr(((double) (-getVirtualLimit_height())) / 55.0d, ((double) getVirtualLimit_height()) / 55.0d);
                    if (AE_UTIL.getRandomNr(0, 1) == 1) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    initateNewObject(new MovingAnimationObject(randomNr, d, randomNr2, randomNr3, randomNr4, randomNr5, diameter_w, diameter_h, i, AEColor.getRandomColor(100,255)));
                }
            }
            if (this.mouseP == null) {
                gameBall.setF_X(0.0d);
                gameBall.setF_Y(0.0d);
                lineToMouse.setW(0);
                lineToMouse.setH(0);
                lineToMouse.setX(0);
                lineToMouse.setY(0);
            } else {
                double[] a_s = AE_UTIL.angleVelocityToXYVelocity((double) AE_UTIL.getAngle(new AEPoint(gameBall.getMidAsPoint().x, gameBall.getMidAsPoint().y), new AEPoint(mouseP.x, mouseP.y)), ((double) getVirtualLimit_height()) / 1.6d);
                gameBall.setF_X(a_s[0]);
                gameBall.setF_Y(a_s[1]);
                lineToMouse.setW(gameBall.getMid().x);
                lineToMouse.setH(gameBall.getMid().y);
                lineToMouse.setX(mouseP.x);
                lineToMouse.setY(mouseP.y);
            }
            gameBall.move(getTicksPerSecond());
            if (gameBall.getX() < (-gameBall.getW()) || gameBall.getX() > ((double) getVirtualLimit_width()) || gameBall.getY() < (-gameBall.getH()) || gameBall.getY() > ((double) getVirtualLimit_height())) {
                this.gameOver = true;
                return;
            }
            List<AnimationObject> obstacles = getObjectsInRange(2, getObjectsCount());
            for (AnimationObject obstacle : obstacles) {
                MovingAnimationObject obstacle2 = (MovingAnimationObject) obstacle;
                if (obstacle2 == null) {
                    obstacles.remove(obstacle2);
                } else {
                    obstacle2.move(getTicksPerSecond());
                    if (AnimationObject.collision(gameBall, obstacle2)) {
                        this.gameOver = true;
                        return;
                    } else if (!new AERect(getVirtualBoundaries()).intersects(obstacle2.getBounds())) {
                        obstacles.remove(obstacle2);
                    }
                }
            }
            this.game_points += 0.01d / ((double) getTicksPerSecond());
            if (newObstacleSpawned) {
                this.game_points += max_av_sps / ((double) getTicksPerSecond());
            }
            if (gameBall.v.getLength() > ((double) getVirtualLimit_height()) / this.average_spawns_per_second) {
                this.game_points += 1.0d / ((double) getTicksPerSecond());
                this.game_points_boost_active = true;
                return;
            }
            this.game_points_boost_active = false;
        }
    }
    public void upandvalidateScores() {
        if (gameBall != null) {
            this.lastScores.add(0, new ScoreSet((int) game_points, ((AEColor)gameBall.drawParam)));
        }
        ScoreSet[] scores = this.lastScores.toArray(new ScoreSet[this.lastScores.size()]);
        this.lastScores.clear();
        int i = 0;
        while (i < 20 && i < scores.length) {
            if (scores[i].getPoints() >= 0 && !this.lastScores.contains(scores[i])) {
                this.lastScores.add(scores[i]);
            }
            i++;
        }
        this.highScores.addAll(this.lastScores);
        scores = this.highScores.toArray(new ScoreSet[this.highScores.size()]);
        Arrays.sort(scores);
        this.highScores.clear();
        i = 0;
        while (i < 10 && i < scores.length) {
            if (scores[i].getPoints() >= 0 && !this.highScores.contains(scores[i])) {
                this.highScores.add(scores[i]);
            }
            i++;
        }
    }
}





















































//OLD - VERY SMALL, no advanced systems
//package evade.game;
//
//		import util.UTIL;
//		import util.animation.engine.AnimationEngine;
//		import util.animation.engine.MovingAnimationObject;
//		import util.animation.pipeline.AnimationObject;
//		import util.animation.util.*;
//
//		import java.util.List;
//
//public class EvadeGameEngine extends AnimationEngine {
////    @Override public Dimension getVirtualBoundaries() {return new Dimension(500, 500); }
////    @Override public double getInitialPixelsPerBox() {
////    	return 0.2;
////    }
//
//	@Override public AESize getVirtualBoundaries() {
//		return new AESize(1920, 1080);
//	}
//	@Override public void initiate() {
//		clearObjects();
//		AEColor randClr = AEColor.AEColor.getRandomColor();
//		initateObjectAt(0, new AnimationObject(null, AnimationObject.LINE,randClr));
//		initateObjectAt(1, new MovingAnimationObject(
//				getVirtualLimit_width()/2,
//				getVirtualLimit_height()/2,
//				0,0,0,0,55,55,
//				MovingAnimationObject.OVAL,randClr));
//	}
//	@Override public void mouseClicked(int mouseCode) {
//		gameBall.drawParam=AEColor.AEColor.getRandomColor();
//		lineToMouse.drawParam=gameBall.drawParam;
//	}
//
//	private AEPoint mouseP;
//	@Override public void locationInputChanged(AEPoint p) {
//		mouseP=p;
//	}
//
//	double lastSpawn = 0;
//	@Override protected void calculateTick() {
//		if(gameBall==null)initiate();
//
//		if(System.nanoTime()/1e9-lastSpawn>0.15) {
//			lastSpawn = System.nanoTime()/1e9;
//			if(UTIL.AE_UTIL.getRandomNr(0, 1) == 1) {
//				double diameter = UTIL.AE_UTIL.getRandomNr(22.0, 55.0);
//				initateNewObject(new MovingAnimationObject(UTIL.AE_UTIL.getRandomNr(0,getVirtualLimit_width()), -diameter+1, UTIL.AE_UTIL.getRandomNr(-11, 11), UTIL.AE_UTIL.getRandomNr(11, 44), UTIL.AE_UTIL.getRandomNr(-11, 11), UTIL.AE_UTIL.getRandomNr(0, 22),
//						diameter, diameter, (UTIL.AE_UTIL.getRandomNr(0, 1) == 1)?MovingAnimationObject.OVAL:MovingAnimationObject.RECT, AEColor.AEColor.getRandomColor()));
//			}
//		}
//
//		if(mouseP!=null) {
//			double[] a_s = AE_UTIL.angleVelocityToXYVelocity(AE_UTIL.getAngle(new AEPoint(gameBall.getMidAsPoint().x, gameBall.getMidAsPoint().y), new AEPoint(mouseP.x, mouseP.y)), 444);
//			((MovingAnimationObject) gameBall).setF_X(a_s[0]);((MovingAnimationObject) gameBall).setF_Y(a_s[1]);
//			((MovingAnimationObject) gameBall).move(getTicksPerSecond());
//			lineToMouse.setW(gameBall.getMid().x);
//			lineToMouse.setH(gameBall.getMid().y);
//			lineToMouse.setX(mouseP.x);
//			lineToMouse.setY(mouseP.y);
//		}
//
//		if((gameBall.overlapingBoundsLeft()||gameBall.overlapingBoundsRight(getVirtualLimit_width())||
//				gameBall.overlapingBoundsTop()||gameBall.overlapingBoundsBottom(getVirtualLimit_height()))) {
//			initateObjectAt(1, null);
//			return;
//		}
//		List<AnimationObject> obstacles = getObjectsInRange(2, getObjectsCount());
//		for(AnimationObject ao:obstacles) {
//			MovingAnimationObject obstacle = (MovingAnimationObject) ao;
//			obstacle.move(getTicksPerSecond());
//			if(AnimationObject.collision(gameBall, obstacle)) {
//				initateObjectAt(1, null);
//				return;
//			} else if (!new AERect(getVirtualBoundaries()).intersects(obstacle.getBounds()))
//				obstacles.remove(obstacle);
//		}
//	}
//
//}
