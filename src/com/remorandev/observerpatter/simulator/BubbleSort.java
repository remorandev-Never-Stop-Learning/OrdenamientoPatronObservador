package com.remorandev.observerpatter.simulator;

import java.util.Arrays;

public class BubbleSort implements Runnable {

    private final SortActionListener listener;
    private final Integer[] array;
    private final long delay;

    public BubbleSort(Integer[] array,long delay, SortActionListener listener){
        this.array = array;
        this.listener = listener;
        this.delay = delay;
    }

    @Override
    public void run() {
        try {

            for (int i = array.length - 1; i > 0; i --){
                for (int j = 0; j < i; j ++){
                    if (array [j] > array [j+1]){
                        int tempo = array [j];
                        array[j] = array [j + 1];
                        array [j+1] = tempo;
                        Thread.sleep(delay);
                        // Notificamos que hubo un cambio en el arreglo, enviando
                        // el String de como es el arraglo depues de cada cambio.
                        listener.onActionListener(Arrays.toString(array));
                    }
                }
            }
            // Notificamos que el proceso ha finalizado 
            listener.onFinish();
        }
        catch (InterruptedException ex){
            Thread.currentThread().interrupt();
            listener.onFinish();
        }
    }
}
