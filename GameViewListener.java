import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameViewListener implements MouseListener, MouseMotionListener {
    private final GameView gameView;
    private final GameController controller;

    private JLabel selectedLabel;
    private BufferedImage draggedImage;
    private boolean dragging = false;
    private Point draggedPosition = new Point();
    private Point dragOffset = new Point();

    public GameViewListener(GameView gameView, GameController controller) {
        this.gameView = gameView;
        this.controller = controller;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component clickedComponent = SwingUtilities.getDeepestComponentAt(gameView, e.getX(), e.getY());

        if (isPlantLabel(clickedComponent)) {
            startDragging((JLabel) clickedComponent, e.getPoint());
        }
        gameView.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragging) {
            draggedPosition.setLocation(e.getPoint());
            gameView.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!dragging || selectedLabel == null) return;

        handlePlantDrop(e.getPoint());
        resetDragState();
        gameView.repaint();
    }

    private boolean isPlantLabel(Component component) {
        ArrayList<JLabel> plantLabels = gameView.getPlantLabels();
        return component instanceof JLabel && plantLabels.contains(component);
    }

    private void startDragging(JLabel label, Point clickPoint) {
        selectedLabel = label;
        dragging = true;

        Point labelPosition = SwingUtilities.convertPoint(label, 0, 0, gameView);
        dragOffset = new Point(clickPoint.x - labelPosition.x, clickPoint.y - labelPosition.y);
        draggedPosition = clickPoint;

        createDraggedImage(label.getName());
    }

    private void createDraggedImage(String plantType) {
        try {
            BufferedImage originalImage = ImageIO.read(new File("assets/plants/" + plantType + ".png"));
            Image scaledImage = originalImage.getScaledInstance(GameView.CELL_WIDTH - 10, GameView.CELL_HEIGHT - 10, Image.SCALE_SMOOTH);
            draggedImage = new BufferedImage(GameView.CELL_WIDTH, GameView.CELL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = draggedImage.createGraphics();
            g2d.drawImage(scaledImage, 5, 5, null);
            g2d.dispose();
        } catch (Exception e) {
            System.err.println("Failed to create dragged image for " + plantType + ": " + e.getMessage());
        }
    }

    private void handlePlantDrop(Point dropPoint) {
        Point gridPosition = gameView.snapToGrid(dropPoint.x, dropPoint.y);

        if (gridPosition != null && controller != null) {
            String plantType = selectedLabel.getName();
            if (plantType != null) {
                controller.placePlantOnBoard(plantType, gridPosition.y, gridPosition.x);
            }
        }
    }

    private void resetDragState() {
        dragging = false;
        draggedImage = null;
        selectedLabel = null;
    }

    public boolean isDragging() {
        return dragging;
    }

    public BufferedImage getDraggedImage() {
        return draggedImage;
    }

    public Point getDraggedPosition() {
        return draggedPosition;
    }

    public Point getDragOffset() {
        return dragOffset;
    }

    // Unused mouse events
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}