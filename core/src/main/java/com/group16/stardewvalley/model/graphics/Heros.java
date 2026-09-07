package com.group16.stardewvalley.model.graphics;

public enum Heros {
    ABIGAIL("sprites/Abigail.png", 16, 32, 2, 0, 1, 3),
    ALEX("sprites/Alex.png", 16, 32, 2, 0, 1, 3),
    KENT("sprites/Kent.png", 16, 32, 2, 0, 1, 3),
    LEO("sprites/Leo.png", 16, 32, 2, 0, 1, 3),
    MARNIE("sprites/Marnie.png", 16, 32, 2, 0, 1, 3);

    private final String texturePath;
    private final int frameWidth;
    private final int frameHeight;
    private final int downRow;
    private final int upRow;
    private final int leftRow;
    private final int rightRow;

    Heros(String texturePath, int frameWidth, int frameHeight,
                  int downRow, int upRow, int leftRow, int rightRow) {
        this.texturePath = texturePath;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.downRow = downRow;
        this.upRow = upRow;
        this.leftRow = leftRow;
        this.rightRow = rightRow;
    }

    public String getTexturePath() { return texturePath; }
    public int getFrameWidth() { return frameWidth; }
    public int getFrameHeight() { return frameHeight; }
    public int getDownRow() { return downRow; }
    public int getUpRow() { return upRow; }
    public int getLeftRow() { return leftRow; }
    public int getRightRow() { return rightRow; }


}
