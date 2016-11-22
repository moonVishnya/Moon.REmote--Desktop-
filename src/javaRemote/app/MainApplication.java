package javaRemote.app;


import javaRemote.net.Callback;
import javaRemote.net.Connecter;
import javaRemote.net.DataManager;
import javaRemote.net.server.ServerInformation;

import java.awt.*;
import java.lang.invoke.SerializedLambda;
import java.net.MalformedURLException;

public class MainApplication extends Thread {

    private static Connecter connecter = new Connecter();
    private static DataManager dataManager = new DataManager();
    private static final int TICK_TIME = 15000;
    final static Object lock = new Object();
    private static final String ON_SUCCESS_MSG = "got signal, start playing";


    private static OnPauseCheckerThread onPauseCheckerThread;
    private static SignalCheckerThread signalCheckerThread;
    private static boolean checkingForSignal;
    private static boolean onPauseCheckingForSignal;

    public static void main(String[] args) {

        checkingForSignal = true;
        onPauseCheckingForSignal = false;

        signalCheckerThread = new SignalCheckerThread();
        signalCheckerThread.start();


    }



    private static class SignalCheckerThread extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                while (checkingForSignal) {
                    try {
                        connecter.createConnection(ServerInformation.SERVER_NAME
                                + ServerInformation.ON_CHECK_FOR_SIGNAL_OPERATION, new Callback() {
                            @Override
                            public void onSuccess() {
                                System.out.println(ON_SUCCESS_MSG);
                                dataManager.onPlay();
                                checkingForSignal = false;
                                onPauseCheckingForSignal = true;
                                onPauseCheckerThread = new OnPauseCheckerThread();

                                onPauseCheckerThread.start();
                            }

                            @Override
                            public void onError() {
                                System.out.println("waiting for signal...");
                                try {
                                    Thread.sleep(TICK_TIME);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    private static class OnPauseCheckerThread extends Thread {



        @Override
        public void run() {
            synchronized (lock) {
                while (onPauseCheckingForSignal) {

                    try {
                        System.out.println("OnPAUSE");
                        connecter.createConnection(ServerInformation.SERVER_NAME
                                + ServerInformation.ON_CHECK_FOR_SIGNAL_OPERATION, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                onPauseCheckingForSignal = false;
                                checkingForSignal = true;
                                dataManager.onStop();
                                new SignalCheckerThread().start();
                            }
                        });

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

}
