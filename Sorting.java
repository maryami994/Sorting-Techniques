package SortingTechniques;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class Sorting extends JFrame {

    private final int[] numbers;
    private final JPanel panel;
    private final Random random;

    public Sorting() {
        super("Sorting Algorithm Visualization");
        random = new Random();
        numbers = new int[10000]; // Array size

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = getWidth() / numbers.length;
                width = Math.max(1, width); // Ensure bar width is at least 1
                int height = getHeight();
                for (int i = 0; i < numbers.length; i++) {
                    int barHeight = (int) ((double) numbers[i] / numbers.length * height);
                    g.setColor(Color.PINK);
                    g.fillRect(i * width, height - barHeight, width, barHeight);
                }
            }
        };
        panel.setPreferredSize(new Dimension(800, 600));
        this.add(panel, BorderLayout.CENTER);

        JButton bubbleSortButton = new JButton("Bubble Sort");
        bubbleSortButton.addActionListener(this::startBubbleSort);
        JButton quickSortButton = new JButton("Quick Sort");
        quickSortButton.addActionListener(this::startQuickSort);
        JButton countingSortButton = new JButton("Counting Sort");
        countingSortButton.addActionListener(this::startCountingSort);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(bubbleSortButton);
        buttonsPanel.add(quickSortButton);
        buttonsPanel.add(countingSortButton);
        this.add(buttonsPanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        initializeArray();
    }

    private void initializeArray() {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(numbers.length);
        }
        repaint();
    }

    private void startBubbleSort(ActionEvent evt) {
        initializeArray();
        new Thread(this::bubbleSort).start();
    }

    private void bubbleSort() {
        boolean swapped;
        for (int i = numbers.length - 1; i > 0; i--) {
            swapped = false;
            for (int j = 0; j < i; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    swap(j, j + 1);
                    swapped = true;
                }
            }
            if (swapped) {
                repaint();
                sleep(20); 
            }
        }
    }

    private void startQuickSort(ActionEvent e) {
        initializeArray(); 
        new SwingWorker<Void, int[]>() {
            @Override
            protected Void doInBackground() throws Exception {
                quickSort(0, numbers.length - 1);
                return null;
            }

            private void quickSort(int low, int high) throws InterruptedException {
                if (low < high) {
                    int pi = partition(low, high);
                    quickSort(low, pi - 1);
                    quickSort(pi + 1, high);
                }
            }

            private int partition(int low, int high) throws InterruptedException {
                int pivot = numbers[high];
                int i = (low - 1);
                for (int j = low; j < high; j++) {
                    if (numbers[j] < pivot) {
                        i++;
                        swap(i, j);
                        publish(numbers.clone()); 
                        Thread.sleep(10); 
                    }
                }
                swap(i + 1, high);
                publish(numbers.clone());
                Thread.sleep(10); 
                return i + 1 ;
                        }

            @Override
            protected void process(java.util.List<int[]> chunks) {
                int[] latestArray = chunks.get(chunks.size() - 1);
                System.arraycopy(latestArray, 0, numbers, 0, latestArray.length);
                repaint();
            }
        }.execute();
    }

    private void startCountingSort(ActionEvent e) {
        new SwingWorker<Void, int[]>() {
            @Override
            protected Void doInBackground() throws Exception {
                int max = getMax();
                int[] count = new int[max + 1];

                for (int number : numbers) {
                    count[number]++;
                }

                int index = 0;
                for (int i = 0; i < count.length; i++) {
                    while (count[i] > 0) {
                        numbers[index++] = i;
                        count[i]--;
                        publish(numbers.clone());
                        Thread.sleep(1);
                    }
                }
                return null;
            }

            @Override
            protected void process(java.util.List<int[]> chunks) {
                int[] latestArray = chunks.get(chunks.size() - 1);
                System.arraycopy(latestArray, 0, numbers, 0, latestArray.length);
                repaint();
            }
        }.execute();
    }

    private int getMax() {
        int max = numbers[0];
        for (int num : numbers) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    private void swap(int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        new Sorting();
    }
}