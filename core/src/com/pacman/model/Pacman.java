package com.pacman.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class Pacman extends Entity {
    public int life;
    public int eatenGhosts = 0;
    public int score = 0;
    public boolean isEnergyMode = false;
    public long lastSoundId = 0L;


    public Pacman(int col, int row, GameMap gameMap) {
        super(col, row, gameMap);
        create();
    }

    public void eatGhost(Ghost ghost) {
        eatenGhosts++;
        ghost.resetPosition(true);
        score += 200 * eatenGhosts;
        ghost.resetPosition(true);
    }

    public void eatNormalFood() {
        score += 5;
        if (lastSoundId != 0) gameMap.gameScreen.pacmanEats.stop(lastSoundId);
        lastSoundId = gameMap.gameScreen.pacmanEats.play(0.1f * gameMap.soundRatio, 1f, 0);
    }

    public void eatEnergyBomb() {
        score += 0;
        isEnergyMode = true;
        gameMap.gameScreen.energyBombEaten.play(gameMap.soundRatio);
        setTimer(10f);
    }

    public void disableEnergyBomb() {
        eatenGhosts = 0;
        isEnergyMode = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(setAnimation().getKeyFrame(gameMap.gameScreen.stateTime, true),
                shape.x, shape.y, TileType.TILE_SIZE, TileType.TILE_SIZE);
    }

    @Override
    public void timerEnd() {
        disableEnergyBomb();
    }


    @Override
    public void update(float delta) {
        isTimerEnded();

        if (Gdx.input.isKeyPressed(Keys.UP)) {
            if (moveCondition != Move.UP) {
                if (!isCollided(Move.UP, delta)) {
                    putAtMiddleOfTile(shape);
                    moveCondition = Move.UP;
                }
            }
        }


        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            if (moveCondition != Move.DOWN) {
                if (!isCollided(Move.DOWN, delta)) {
                    putAtMiddleOfTile(shape);
                    moveCondition = Move.DOWN;
                }
            }
        }


        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            if (moveCondition != Move.RIGHT) {
                if (!isCollided(Move.RIGHT, delta)) {
                    putAtMiddleOfTile(shape);
                    moveCondition = Move.RIGHT;
                }
            }
        }


        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            if (moveCondition != Move.LEFT) {
                if (!isCollided(Move.LEFT, delta)) {
                    putAtMiddleOfTile(shape);
                    moveCondition = Move.LEFT;
                }
            }
        }


        move(delta);
//        TileType tileType = gameMap.getTileTypeByLocation(0, getMiddleXOfShape(), getMiddleYOfShape());
        switch (gameMap.getTileTypeByLocation(0, getMiddleXOfShape(), getMiddleYOfShape())) {
            case FOOD:
                gameMap.eatTileTypeContent(0, getMiddleXOfShape(), getMiddleYOfShape());
                this.eatNormalFood();
                break;
            case ENERGY_BOMB:
                gameMap.eatTileTypeContent(0, getMiddleXOfShape(), getMiddleYOfShape());
                this.eatEnergyBomb();
                break;
            case NONE:
                break;
        }
    }

    @Override
    public void setImage(Texture texture) {

    }

    @Override
    public void create() {
        moveCondition = Move.UP;
        nowImage = new Texture(Gdx.files.internal("pacman.png"));
        setImage(nowImage);
        setSpeed(65);
        createFrames(0,0);
    }

    @Override
    public void resetPosition(boolean isEaten) {
        if (isEaten) {
            for (Ghost ghost : gameMap.ghosts)
                ghost.resetPosition(false);

            setShape(firstCol, firstRow);
        }


    }

    public void move(float delta) {
        if (!isCollided(moveCondition, delta)) {
            doTheMove(delta);
        } else {
            putAtMiddleOfTile(shape);
        }
    }

    public void overlapWithGhost(Ghost ghost) {
        if (isEnergyMode) {
            eatGhost(ghost);
            gameMap.gameScreen.ghostsDie.play(gameMap.soundRatio);
        } else {
            life--;
            gameMap.isGamePaused = true;
            if (life == 0) {
                gameMap.gameScreen.gameOverSound.play(gameMap.soundRatio);
                gameMap.gameScreen.gameOver();
            } else {
                gameMap.gameScreen.pacmanDies.play(gameMap.soundRatio);
            }
            this.resetPosition(true);
        }
    }

    public void createFrames(int startRow, int startCol) {
        myFrames = new HashMap<>();
        for (Move move : new Move[]{Move.UP, Move.DOWN, Move.RIGHT, Move.LEFT}) {
            TextureRegion[] textureRegion = new TextureRegion[3];
            for (int i = startCol; i < startCol + 3; i++) {
                textureRegion[i - startCol] = gameMap.gameScreen.tmp_pacman[startRow][i];
            }
            myFrames.put(move, textureRegion);
            startRow++;
        }
    }

    public Animation<TextureRegion> setAnimation() {
        walkAnimation = new Animation<TextureRegion>(0.25f
                , myFrames.get(moveCondition));
        return walkAnimation;
    }
}
