package at.gotzi.drawmachineapp;

import at.gotzi.drawmachineapp.api.Application;

import java.io.IOException;

public class DrawMachineAppLoader {

    public static void main(String[] args) throws IOException {
        Application application = new DrawMachineApp();
        application.start();
    }

}
