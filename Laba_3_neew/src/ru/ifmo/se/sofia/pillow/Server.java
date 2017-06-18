package ru.ifmo.se.sofia.pillow;

import org.postgresql.util.PSQLException;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public class Server {

    public static final int PORT = 6666;

    private DatagramSocket socket;
    private Database database;

    public Server() {

        try {
            database = new Database();
        } catch (SQLException e) {
            System.out.println("Возникли проблемы при подключении к базе: " + e.getMessage());
        }


    }

    public static void main(String[] args) {
            new Server().run();

    }

    public void run() {

        bind();

        while (true) {
            try {
                receive();
            } catch (IOException e) {
                // FIXME
                e.printStackTrace();
            }
        }
    }

    private void bind() {
        try {
            SocketAddress address = new InetSocketAddress(PORT);
            socket = new DatagramSocket(address);
        } catch (SocketException e) {
            // Unreachable
        }
    }

    private void send(SocketAddress socketAddress, byte[] bytes) {
        try {
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, socketAddress);
            System.out.printf("--> %s : %d bytes : %s%n", socketAddress, bytes.length, Arrays.toString(bytes));
            socket.send(packet);
        } catch (IOException e) {
            // TODO: Process IOException
            e.printStackTrace();
        }
    }

    private void receive() throws IOException {
        try {
            final byte bytes[] = new byte[4096];
            final DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
            socket.receive(packet);
            new Thread(() -> {
                try {
                    // Identify client
                    SocketAddress client = packet.getSocketAddress();
                    System.out.printf("<-- %s : %d bytes : %s%n", client, packet.getLength(), Arrays.toString(Arrays.copyOfRange(bytes, packet.getOffset(), packet.getOffset() + packet.getLength())));

                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes, packet.getOffset(), packet.getLength());
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

                    long clientVersion = objectInputStream.readLong();
                    byte requestCode = objectInputStream.readByte();
                    Optional<Request> request = Request.withCode(requestCode);

                    if (clientVersion < database.getVersion()) {
                        // TODO: Notify client of being out of date and halt
                        respondOutOfDate(client);
                    }

                    if (request.isPresent()) {
                        switch (request.get()) {
                            case ADD:
                                try {
                                    Ghost ghost = (Ghost) objectInputStream.readObject();
                                    database.add(ghost);
                                    respondOK(client);
                                } catch (ClassCastException | ClassNotFoundException e) {
                                    // TODO: Notify client of invalid object
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    System.out.println("Catched SQLException: " + e.getMessage());
                                }
                                break;
                            case DELETE:
                                try {
                                    String ghostName = (String) objectInputStream.readObject();
                                    database.delete(ghostName);
                                    respondOK(client);
                                } catch (ClassCastException | ClassNotFoundException e) {
                                    // TODO: Notify client of invalid object
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    System.out.println("Catched SQLException: " + e.getMessage());
                                }
                                break;
                            case UPDATE:
                                try {
                                    String ghostName = (String) objectInputStream.readObject();
                                    Ghost updated = (Ghost) objectInputStream.readObject();
                                    database.update(ghostName, updated);
                                    respondOK(client);
                                } catch (ClassCastException | ClassNotFoundException e) {
                                    // TODO: Notify client of invalid object
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    System.out.println("Catched SQLException: " + e.getMessage());
                                }
                                break;
                            case LIST:
                                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096)) {
                                    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                                        objectOutputStream.writeByte(Response.LIST.getCode());
                                        objectOutputStream.writeObject(database.getGhosts());
                                        send(client, byteArrayOutputStream.toByteArray());
                                    } catch (SQLException e) {
                                        System.out.println("Catched SQLException: " + e.getMessage());
                                    }
                                }
                                break;
                            case CLEAR:
                                database.clear();
                                respondOK(client);
                                break;
                            case REMOVE_LOWER:
                                try {
                                    String ghostName = (String) objectInputStream.readObject();
                                    database.removeLower(ghostName);
                                    respondOK(client);
                                } catch (ClassCastException | ClassNotFoundException e) {
                                    // TODO: Notify client of invalid object
                                    e.printStackTrace();
                                }
                                break;
                        }
                    } else {
                        // TODO: Notify client of invalid query
                    }
                } catch (IOException e) {
                    // TODO: Process network errors
                    e.printStackTrace();
                } catch (SQLException e) {
                    System.out.println("Catched SQLException: " + e.getMessage());
                }
            }).start();

        } catch (SocketTimeoutException e) {
            System.out.println("Истекло время ожидания");
        }
    }

    private void respondOutOfDate(SocketAddress client) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096)) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                objectOutputStream.writeByte(Response.OUT_OF_DATE.getCode());
            }
            send(client, byteArrayOutputStream.toByteArray());
        }
    }

    private void respondOK(SocketAddress client) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096)) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                objectOutputStream.writeByte(Response.OK.getCode());
            }
            send(client, byteArrayOutputStream.toByteArray());
        }
    }
}
