package at.gotzi.drawmachine;

import at.gotzi.drawmachine.api.Application;

import java.io.IOException;

public class DrawMachineAppLoader {

    public static void main(String[] args) throws IOException {
        Application application = new DrawMachineCA();
        application.start();
    }

}
