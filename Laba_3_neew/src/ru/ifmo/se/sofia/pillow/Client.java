package ru.ifmo.se.sofia.pillow;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Client {

    private static SocketAddress serverAddress;
    private static long version;
    private final DatagramChannel datagramChannel;
    static MainWindow w;

    public Client() throws IOException {
        datagramChannel = DatagramChannel.open();
        datagramChannel.bind(null);
    }

    public static void main(String[] args) throws IOException {
        new Client().run();
    }

    void run() {
        version = 1;
        InetAddress localhost = null;
        try {
            localhost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // Unreachable
        }
        serverAddress = new InetSocketAddress(localhost, Server.PORT);
        w = new MainWindow(this);
        w.setVisible(true);
        w.setResizable(false);
    }


    void send(String str) {
        try {
            ByteBuffer f = ByteBuffer.allocate(2048);
            f.clear();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(bout);
            oout.writeObject(version + " " + str);
            f.put(bout.toByteArray());
            f.flip();
            int ByteSend = datagramChannel.send(f, serverAddress);
        } catch (IOException e) {
            System.out.println("Can't open channel");
        }
    }

    boolean receive() {
        try {
            datagramChannel.socket().setSoTimeout(3_000);
            byte bytes[] = new byte[4096];
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
            datagramChannel.socket().receive(packet);
            System.out.printf("<-- %s : %d bytes : %s%n", packet.getSocketAddress(), packet.getLength(), Arrays.toString(Arrays.copyOfRange(bytes, packet.getOffset(), packet.getOffset() + packet.getLength())));

            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes, packet.getOffset(), packet.getLength())) {
                try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

                    byte responseCode = objectInputStream.readByte();
                    Optional<Response> response = Response.withCode(responseCode);

                    if (response.isPresent()) {
                        System.out.println(response.get());
                        switch (response.get()) {
                            case LIST:
                                try {
                                    ArrayList<Ghost> ghosts = (ArrayList<Ghost>) objectInputStream.readObject();
                                    MainWindow.shownArr.clear();
                                    MainWindow.realArr.clear();
                                    MainWindow.shownArr.addAll(ghosts);
                                    MainWindow.realArr.addAll(ghosts);
                                    Tree.updateTree(MainWindow.shownArr, MainWindow.realArr, w.Zone);
                                } catch (ClassCastException | ClassNotFoundException e) {
                                    // TODO: Process invalid server response
                                }
                                return true;
                            case OK:
                                version++;
                                return true;
                            case OUT_OF_DATE:
                                requestList();
                                return false;
                        }
                    } else {
                        // TODO: Process unexpected server response
                    }
                }
            }
        } catch (SocketTimeoutException e) {
            //new PillowException("Сервер прилёг поспать :(");
            new PillowException("На сервере проблемы..");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    void requestList() {
        try {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096)) {
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                    objectOutputStream.writeLong(version);
                    objectOutputStream.writeByte(Request.LIST.getCode());
                }
                datagramChannel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), serverAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void requestClear() {
        try {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096)) {
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                    objectOutputStream.writeLong(version);
                    objectOutputStream.writeByte(Request.CLEAR.getCode());
                }
                datagramChannel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), serverAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void requestAdd(Ghost ghost) {
        try {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096)) {
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                    objectOutputStream.writeLong(version);
                    objectOutputStream.writeByte(Request.ADD.getCode());
                    objectOutputStream.writeObject(ghost);
                }
                datagramChannel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), serverAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void requestDelete(String ghostName) {
        try {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096)) {
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                    objectOutputStream.writeLong(version);
                    objectOutputStream.writeByte(Request.DELETE.getCode());
                    objectOutputStream.writeObject(ghostName);
                }
                datagramChannel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), serverAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void requestRemoveLower(String ghostName) {
        try {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096)) {
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                    objectOutputStream.writeLong(version);
                    objectOutputStream.writeByte(Request.REMOVE_LOWER.getCode());
                    objectOutputStream.writeObject(ghostName);
                }
                datagramChannel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), serverAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestUpdate(String ghostName, Ghost updated) {
        try {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096)) {
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                    objectOutputStream.writeLong(version);
                    objectOutputStream.writeByte(Request.UPDATE.getCode());
                    objectOutputStream.writeObject(ghostName);
                    objectOutputStream.writeObject(updated);
                }
                datagramChannel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), serverAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
