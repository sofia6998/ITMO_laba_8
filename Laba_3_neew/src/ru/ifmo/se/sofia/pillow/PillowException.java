package ru.ifmo.se.sofia.pillow;

class PillowException extends Exception {
    PillowException(String ss) {
        MainWindow.statusBar.setText(ss);
    }
}
