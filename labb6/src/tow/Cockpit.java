package tow;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class Cockpit extends KeyAdapter {      // Interpreting keyboard actions into game actions

    private final int MAX_STEER = 40;
    private final int STEER_INCREMENT = 2;
    private final double VELOCITY_INCREMENT;
    private final double BRAKE_INCREMENT;
    private final double MAX_VELOCITY;
    private final double MIN_VELOCITY;

    private int steeringWheelAngle;

    int getSteeringWheelAngle() {
        return steeringWheelAngle;
    }

    private double velocity;

    double getVelocity() {
        return velocity;
    }

    private boolean isTurningLeft, isTurningRight, isAcceleratingForward, isAcceleratingBackward, isBraking;

    Cockpit() {

        VELOCITY_INCREMENT = 0.2 * GameArea.scale;
        BRAKE_INCREMENT = VELOCITY_INCREMENT * 2;
        MAX_VELOCITY = 15 * GameArea.scale;
        MIN_VELOCITY = -2 * GameArea.scale;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                isTurningLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
                isTurningRight = true;
                break;
            case KeyEvent.VK_UP:
                isAcceleratingForward = true;
                break;
            case KeyEvent.VK_DOWN:
                isAcceleratingBackward = true;
                break;
            case KeyEvent.VK_SPACE:
                isBraking = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                isTurningLeft = false;
                break;
            case KeyEvent.VK_RIGHT:
                isTurningRight = false;
                break;
            case KeyEvent.VK_UP:
                isAcceleratingForward = false;
                break;
            case KeyEvent.VK_DOWN:
                isAcceleratingBackward = false;
                break;
            case KeyEvent.VK_SPACE:
                isBraking = false;
                break;
        }
    }

    void changeState() {

        if (isTurningLeft) {
            turnLeft();
        }
        if (isTurningRight) {
            turnRight();
        }
        if (isAcceleratingForward) {
            accelerateForward();
        }
        if (isAcceleratingBackward) {
            accelerateBackward();
        }
        if (isBraking) {
            brake();
        }
    }

    void resetKeys() {

        isTurningLeft = false;
        isTurningRight = false;
        isAcceleratingForward = false;
        isAcceleratingBackward = false;
        isBraking = false;
        velocity = 0;
    }

    private void turnLeft() {
        if (steeringWheelAngle > -MAX_STEER) {
            steeringWheelAngle -= STEER_INCREMENT;
        }
    }

    private void turnRight() {

        if (steeringWheelAngle < MAX_STEER) {
            steeringWheelAngle += STEER_INCREMENT;
        }
    }

    private void accelerateForward() {

        if (velocity >= 0 && velocity < MAX_VELOCITY) {
            velocity += VELOCITY_INCREMENT;
        }
    }

    private void accelerateBackward() {

        if (velocity <= 0 && velocity > MIN_VELOCITY) {
            velocity -= VELOCITY_INCREMENT;
        }
    }

    private void brake() {

        if (velocity > 2 * BRAKE_INCREMENT) {
            velocity -= BRAKE_INCREMENT;
        } else if (velocity < -2 * BRAKE_INCREMENT) {
            velocity += BRAKE_INCREMENT;
        } else {
            velocity = 0;
        }
    }
}
