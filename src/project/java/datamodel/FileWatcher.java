package project.java.datamodel;

import project.java.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
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
                        controller.placeVehicles();
                        System.out.println("ispis");
                    }
                    //compositions watch
                    else if(file.getParent().equals(rootPath) && controller.isSimulationStarted()){
                        controller.initializeComposition(file.toFile());
                    }
                }
            }
        }catch (Exception e){
            //logger
        }
    }
}
