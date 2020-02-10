/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordenamientopatronobservador;

import com.remorandev.observerpatter.simulator.BubbleSort;
import com.remorandev.observerpatter.simulator.SortActionListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author rmoran
 */
public class OrdenamientoPatronObservador extends Application {

    public static final long DELAY_TIME = 1000;
    
    @Override
    public void start(Stage primaryStage) {
        // Creamos grid para poder ubicar los compnentes.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);

        // Variable donde se actualizará el estado del ordenamiento en burbuja
        Text lbBubble = new Text("");

        Button btnStart = new Button();
        btnStart.setText("Iniciar");
        // Agregamos el manejo de evento clic para inciar el ordenamiento
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Array para pruebas, si hay mas procesos seria uno por proceso.
                Integer[] array = {3, 7, 1, 3, 9, 1, 3, 2, 5, 6};
                
                // Iniciamos el método startBubbleSortThread que iniciara el  
                // hilo de ejecución y actualizara el texto de forma dinámica.
                // Para agregar más procesos solo debes replicar este método  
                // con los nuevos ordenamientos y su Text correspondiente.
                startBubbleSortThread(array, DELAY_TIME, lbBubble);
            }
        });

        // Agregamos el boton y lbBubble en las posiciones del grid.
        // Agregar más componentes Text segun cada proceso de ordenamiento nuevo.
        grid.add(btnStart, 0, 0);
        grid.add(new Label("Burbuja"), 0, 1);
        grid.add(lbBubble, 1, 1);

        // Código para agregar el grid la escena
        StackPane root = new StackPane();
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Ordenamiento en Tiempo Real!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * 
     * @param a         Arreglo para ordenamiento
     * @param delay     Tiempo de retraso entre acción del ordenamiento
     * @param text      Varible a actualizar en escena por cada acción
     */
    public static void startBubbleSortThread(Integer[] array,long delay, Text text){
// Iniciamos un hilo independiente para el proceso de ordenamiento
        // y Creamos la clase BubbleSort con el array he implementamos la interface
        // SortActionListener encargada de observar los cambios del proceso,
        // implementando asi el patron de observador.
         Thread thBubble = new Thread(new BubbleSort(array, delay, new SortActionListener() {
                            @Override
                            public void onActionListener(String value) {
                                // Cada vez que cambie el valor lo reciviremos aqui
                                // y actualizaremos la interfaces con Platform.runLater
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        text.setText(value);
                                    }
                                });
                            }

                            @Override
                            public void onFinish() {                                
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        text.setText(text.getText() + " - FINALIZADO");
                                    }
                                });
                            }
                        }));
                
                thBubble.setDaemon(true);
                thBubble.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
