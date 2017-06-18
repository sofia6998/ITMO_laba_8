package ru.ifmo.se.sofia.pillow;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class Commands {
    /**
     * Данная функция сохраняет текущее состояние коллекции в файл, заданный параметром окружения
     */
    public static void save() {
        JSON.saveGhost();
    }

    public static void delete(String ghostName) {
        if (!MainWindow.shownArr.removeIf(g -> g.getName().equals(ghostName))) {
            new PillowException("Ghost not found");
        }
    }

    public static void add(Ghost ghost) {
        if (!MainWindow.shownArr.contains(ghost)) {
            MainWindow.shownArr.add(ghost);
        } else {
            new PillowException("Ghost already exists");
        }
    }

    /**
     * Данная функция удаляет всех приведений младше заданного
     *
     * @param name - имя привидения
     */

    public static void removeLower(String name) {
        Optional<Ghost> ghost = MainWindow.shownArr.stream()
                .filter((Ghost g) -> g.getName().equals(name))
                .findFirst();

        if (ghost.isPresent()) {
            MainWindow.shownArr.removeIf(g -> g.getBirth().isBefore(ghost.get().getBirth()));
        } else {
            new PillowException("Ghost not found");
        }
    }

    /**
     * данная функция добавляет в коллекцию объекты из файла
     *
     * @param filename имя файла
     * @throws FileNotFoundException
     */
    public static void imp(final String filename, Client client) {

        new Thread(() -> {
//            try {
//                JSON.SendGhost(filename);
//            } catch (PillowException e) {
//                e.printStackTrace();
//            }
            JSON.loadFile(MainWindow.realArr, filename, client, MainWindow.Zone);
            JSON.loadFile(MainWindow.shownArr, filename, client, MainWindow.Zone);
            Tree.updateTree(MainWindow.shownArr, MainWindow.realArr, MainWindow.Zone);
        }).start();
    }

    /**
     * Данная функция очищает коллекцию
     */
    public static void clear() {
        MainWindow.shownArr.clear();
        MainWindow.realArr.clear();
        new Thread(() -> JSON.saveGhost()).start();
        System.out.println("Молодец!");
    }

    public static void sortTree(ArrayList<Ghost> arr, int param) {
        switch (MainWindow.sort_type) {
            case 0:
                Collections.sort(MainWindow.shownArr, (ghost, t1) -> ghost.getName().charAt(0) - t1.getName().charAt(0));
                break;
            case 2:
                Collections.sort(arr, (ghost, t1) -> ghost.getHeight() - t1.getHeight());
                break;
            default:
                Collections.sort(arr);
                break;
        }
    }

    public static void change(String s, Ghost newGhost) {

        boolean flag = true;
        try {

            for (int i = 0; i < MainWindow.shownArr.size(); i++) {
                if (MainWindow.shownArr.get(i).getName().equals(s)) {
                    if (s.equals(newGhost.getName()) || !MainWindow.shownArr.contains(newGhost)) {
                        MainWindow.shownArr.set(i, newGhost);
                        flag = false;
                    } else {
                        new PillowException("Ghost already exist");
                    }
                }
            }
        } catch (NumberFormatException ex) {
            new PillowException("Wrong format exception");
        }


        if (flag) {
            new PillowException("Ghost not found");
        }
    }
}