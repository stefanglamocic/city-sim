package project.java.datamodel;

import javafx.application.Platform;
import project.java.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.LinkedList;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatcher implements Runnable{
    private Controller controller;
    private final Path rootPath;

    public FileWatcher(Controller controller, Path rootPath){
        this.controller = controller;
        this.rootPath = rootPath;
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run(){
        try(WatchService watchService = FileSystems.getDefault().newWatchService()){
            WatchKey watchKey = rootPath.register(watchService, ENTRY_MODIFY);

            while(true){
                for(WatchEvent<?> event : watchKey.pollEvents()){
                    Path file = rootPath.resolve((Path) event.context());
                    //config watch
                    if(file.equals(controller.configPath)){
                        try(InputStream inputStream = new FileInputStream(file.toString())){
                            controller.properties.load(inputStream);
                        }catch (IOException e){
                            //logger
                        }
                        controller.loadProperties();
                        int lastCount = controller.carsGeneratedLeft + controller.carsGeneratedMiddle + controller.carsGeneratedRight;
                        Platform.runLater(() -> controller.placeVehicles());
                        startVehicles(lastCount);
                    }
                    //compositions watch
                    else if(file.getParent().equals(rootPath) && controller.isSimulationStarted() && !file.toString().contains("~")){
                        Thread thread = new Thread(() -> {
                            try{
                                Thread.sleep(2000);
                            }catch (InterruptedException e){
                                //logger
                            }
                            controller.initializeComposition(file.toFile());
                        });
                        thread.start();
                    }
                }
            }
        }catch (Exception e){
            //logger
        }
    }

    private void startVehicles(int lastCount){
        Thread thread = new Thread(() -> {
            LinkedList<Vehicle> vehicles = controller.getVehiclesList();
            for(int i = lastCount; i < vehicles.size(); i++){
                ((RoadVehicle)vehicles.get(i)).go();
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    //logger
                }
            }
        });
        thread.start();
    }
}
