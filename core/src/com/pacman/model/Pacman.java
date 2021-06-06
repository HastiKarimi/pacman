package com.pacman.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Pacman extends Entity {
    public int life;    //TODO create data for GameScreen
    public int eatenGhosts = 0;
    public int score = 0;
    public boolean isEnergyMode = false;

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
    }

    public void eatEnergyBomb() {
        score += 0;
        isEnergyMode = true;
        setTimer(10f);
    }

    public void disableEnergyBomb() {
        eatenGhosts = 0;
        isEnergyMode = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(nowImage, shape.x, shape.y);
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
        setSpeed(60);
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
        } else {
            life--;
            gameMap.isGamePaused = true;
            if (life == 0) {
                gameMap.gameScreen.gameOver();
            }
            this.resetPosition(true);
        }
    }
}
