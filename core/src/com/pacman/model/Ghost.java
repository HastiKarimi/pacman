package com.pacman.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class Ghost extends Entity {
    public Random random;
    public boolean canMove = false;
    public Move firstMove;
    public boolean isAvailable = false;
    public Animation<TextureRegion> walkAnimation;
    public static Animation<TextureRegion> otherAnimation;
    public static HashMap<Move, TextureRegion[]> otherFrames;

    static {
        otherFrames = new HashMap<>();
    }

    {
        random = new Random();
    }

    public Ghost(int col, int row, GameMap gameMap, Move firstMove, int startRowAnimation, int startColAnimation) {
        super(col, row, gameMap);
        firstRow = row;
        firstCol = col;
        this.firstMove = firstMove;
        moveCondition = firstMove;
        create(startRowAnimation, startColAnimation);
    }

    public void resetPosition(boolean isEaten) {
        shape.x = firstCol * size + gameMap.spaceX;
        shape.y = firstRow * size + gameMap.spaceY;
        canMove = false;
        if (isEaten) setTimer(5.f);
        else setTimer(2f);

        isAvailable = false;
    }

    public ArrayList<Move> capableOfMoves(float delta) {
        ArrayList<Move> probableMoves = new ArrayList<>();

        for (Move move : new Move[]{Move.UP, Move.DOWN, Move.LEFT, Move.RIGHT}) {
            if (moveCondition != move) {
                if (!isCollided(move, delta))
                    probableMoves.add(move);
            }
        }

        return probableMoves;
    }

    @Override
    public void update(float delta) {
        if (canMove || isTimerEnded()) {
            move(delta);
        }
    }

    @Override
    public void timerEnd() {
        canMove = true;
        moveCondition = firstMove;
        isAvailable = true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(setAnimation().getKeyFrame(gameMap.gameScreen.stateTime, true)
                , shape.x, shape.y);
    }

    public void create(int startRowAnimation, int startColAnimation) {
        createFrames(startRowAnimation, startColAnimation);
        setTimer(2f);
        setSpeed(45);
    }

    public void createFrames(int startRow, int startCol) {
        myFrames = new HashMap<>();
        for (Move move : new Move[]{Move.DOWN, Move.LEFT, Move.RIGHT, Move.UP}) {
            TextureRegion[] textureRegion = new TextureRegion[6];
            for (int i = startCol; i < startCol + 6; i++) {
                textureRegion[i - startCol] = gameMap.gameScreen.tmp[startRow][i];
            }
            myFrames.put(move, textureRegion);
            startRow++;
        }
    }

    public Animation<TextureRegion> setAnimation() {
        if (!gameMap.pacman.isEnergyMode) {
            walkAnimation = new Animation<TextureRegion>(0.25f
                    , myFrames.get(moveCondition));
            return walkAnimation;
        } else {
            otherAnimation = new Animation<TextureRegion>(0.25f,
                    otherFrames.get(moveCondition));
            return otherAnimation;
        }
    }
}