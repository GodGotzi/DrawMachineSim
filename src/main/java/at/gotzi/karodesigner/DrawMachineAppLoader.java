package at.gotzi.karodesigner;

import at.gotzi.karodesigner.api.Application;

import java.io.IOException;

public class DrawMachineAppLoader {

    public static void main(String[] args) throws IOException {
        Application application = new DrawMachineApp();
        application.start();
    }

}
