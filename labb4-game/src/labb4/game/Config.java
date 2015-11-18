package labb4.game;

import java.awt.*;

public class Config {
    public static int FRAMES_PER_SECOND = 100;

    public static final double RESIZE_FACTOR = 3; // resize table/ball/hole size

    public static final double DEFAULT_FRICTION = 0.015;
    public static final double DEFAULT_MASS = 1.0;

    /**
     * Table constants.
     */
    public static final Color TABLE_COLOR = new Color(9, 226, 0);
    public static final Color TABLE_LINE_COLOR = new Color(9, 202, 0);
    public static final Color TABLE_OUTER_BORDER_COLOR = new Color(85, 58, 34);
    public static final Color TABLE_INNER_BORDER_COLOR = new Color(7, 116, 0);

    public static final int DEFAULT_TABLE_WIDTH = (int) (254 * RESIZE_FACTOR);
    public static final int DEFAULT_TABLE_HEIGHT = (int) (127 * RESIZE_FACTOR);

    public static final int TABLE_OUTER_BORDER_SIZE = (int) (6 * RESIZE_FACTOR);
    public static final int TABLE_INNER_BORDER_SIZE = (int) (5 * RESIZE_FACTOR);

    public static final int SNOOKER_TABLE_WIDTH = (int) (356 * RESIZE_FACTOR);
    public static final int SNOOKER_TABLE_HEIGHT = (int) (178 * RESIZE_FACTOR);

    public static final double DEFAULT_X_LINE = 1.0 / 4.0;
    public static final double SNOOKER_X_LINE = 1.0 / 5.0;

    public static final int LINE_SIZE = 3;

    /**
     * Ball constants.
     */
    public static final int BALL_RADIUS = (int) (5 * RESIZE_FACTOR);
    public static final int BALL_BORDER_SIZE = 2;

    public static final Color BALL_BORDER_COLOR = new Color(34, 34, 34);

    public static final Color BALL_WHITE_COLOR = new Color(255, 253, 241);
    public static final Color BALL_LIGHT_GRAY_COLOR = new Color(197, 195, 184);
    public static final Color BALL_BLACK_COLOR = new Color(21, 21, 21);
    public static final Color BALL_PINK_COLOR = new Color(218, 96, 195);
    public static final Color BALL_YELLOW_COLOR = new Color(211, 195, 0);
    public static final Color BALL_BLUE_COLOR = new Color(0, 105, 215);
    public static final Color BALL_RED_COLOR = new Color(198, 8, 0);
    public static final Color BALL_PURPLE_COLOR = new Color(105, 31, 171);
    public static final Color BALL_ORANGE_COLOR = new Color(207, 115, 48);
    public static final Color BALL_GREEN_COLOR = new Color(48, 162, 42);
    public static final Color BALL_BROWN_COLOR = new Color(126, 85, 54);

    /**
     * Pocket constants.
     */
    public static final Color DEFAULT_POCKET_COLOR = new Color(34, 34, 34);
    public static final int DEFAULT_POCKET_RADIUS = (int) (8 * RESIZE_FACTOR);
    public static final int POCKET_COUNT = 6;

    /**
     * Debugging constants.
     */
    public static boolean DISPLAY_BOUNDING_BOXES = false;
    public static boolean DISPLAY_VELOCITY_VECTORS = false;
    public static boolean HIDE_BALLS = false;
}
