package ru.ifmo.se.sofia.pillow;

import javax.swing.*;

public class Layouts {
    public static void create() {
        MainWindow.pp.add(MainWindow.sortL);
        MainWindow.pp.add(MainWindow.sortB);
        MainWindow.pp.add(MainWindow.importButton);
        MainWindow.pp.add(MainWindow.tree);
        MainWindow.pp.add(MainWindow.rb);
        MainWindow.pp.add(MainWindow.sortList);
        MainWindow.pp.add(MainWindow.importFilenameScrollPane);
        MainWindow.pp.add(MainWindow.importButton);
        MainWindow.pp.add(MainWindow.name);
        MainWindow.pp.add(MainWindow.removeLowerButton);
        MainWindow.pp.add(MainWindow.del);
        MainWindow.pp.add(MainWindow.addButton);
        MainWindow.pp.add(MainWindow.deleteButton);
        MainWindow.pp.add(MainWindow.selectGhost);
        MainWindow.pp.add(MainWindow.nameToChange);
        MainWindow.pp.add(MainWindow.changeTo);
        MainWindow.pp.add(MainWindow.updatedInfo);
        MainWindow.pp.add(MainWindow.statusBar);
        MainWindow.pp.add(MainWindow.language);
        MainWindow.pp.add(MainWindow.languageList);

        //language
        MainWindow.layout.putConstraint(SpringLayout.SOUTH, MainWindow.language, -5, SpringLayout.SOUTH, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.WEST, MainWindow.language, 160, SpringLayout.WEST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.SOUTH, MainWindow.languageList, -30, SpringLayout.SOUTH, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.WEST, MainWindow.languageList, 160, SpringLayout.WEST, MainWindow.pp);



        //problems
        MainWindow.layout.putConstraint(SpringLayout.SOUTH, MainWindow.statusBar, 0, SpringLayout.SOUTH, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.WEST, MainWindow.statusBar, 0, SpringLayout.WEST, MainWindow.pp);


        //tree
        MainWindow.layout.putConstraint(SpringLayout.WEST, MainWindow.tree, 5, SpringLayout.WEST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.NORTH, MainWindow.tree, 5, SpringLayout.NORTH, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.SOUTH, MainWindow.tree, 300, SpringLayout.NORTH, MainWindow.tree);
        MainWindow.layout.putConstraint(SpringLayout.EAST, MainWindow.tree, 150, SpringLayout.WEST, MainWindow.tree);

        //sort & filter
        MainWindow.layout.putConstraint(SpringLayout.WEST, MainWindow.sortL, 5, SpringLayout.WEST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.WEST, MainWindow.sortList, 5, SpringLayout.WEST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.WEST, MainWindow.sortB, 5, SpringLayout.WEST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.WEST, MainWindow.rb, 5, SpringLayout.WEST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.NORTH, MainWindow.sortL, 5, SpringLayout.SOUTH, MainWindow.tree);
        MainWindow.layout.putConstraint(SpringLayout.NORTH, MainWindow.sortList, 5, SpringLayout.SOUTH, MainWindow.sortL);
        MainWindow.layout.putConstraint(SpringLayout.NORTH, MainWindow.sortB, 5, SpringLayout.SOUTH, MainWindow.sortList);
        MainWindow.layout.putConstraint(SpringLayout.NORTH, MainWindow.rb, 5, SpringLayout.SOUTH, MainWindow.sortB);

        //import
        MainWindow.layout.putConstraint(SpringLayout.EAST, MainWindow.importFilenameScrollPane, -10, SpringLayout.EAST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.EAST, MainWindow.importButton, -10, SpringLayout.EAST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.NORTH, MainWindow.importFilenameScrollPane, 5, SpringLayout.NORTH, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.NORTH, MainWindow.importButton, 2, SpringLayout.SOUTH, MainWindow.importFilenameScrollPane);
        MainWindow.layout.putConstraint(SpringLayout.SOUTH, MainWindow.importFilenameScrollPane, 50, SpringLayout.NORTH, MainWindow.importFilenameScrollPane);
        MainWindow.layout.putConstraint(SpringLayout.WEST, MainWindow.importFilenameScrollPane, -200, SpringLayout.EAST, MainWindow.importFilenameScrollPane);


        //removeLower
        MainWindow.layout.putConstraint(SpringLayout.EAST, MainWindow.name, -30, SpringLayout.EAST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.EAST, MainWindow.removeLowerButton, -30, SpringLayout.EAST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.NORTH, MainWindow.name, 200, SpringLayout.NORTH, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.NORTH, MainWindow.removeLowerButton, 2, SpringLayout.SOUTH, MainWindow.name);
        MainWindow.layout.putConstraint(SpringLayout.SOUTH, MainWindow.name, 40, SpringLayout.NORTH, MainWindow.name);
        MainWindow.layout.putConstraint(SpringLayout.WEST, MainWindow.name, -200, SpringLayout.EAST, MainWindow.name);


        //deleteButton
        MainWindow.layout.putConstraint(SpringLayout.EAST, MainWindow.del, -30, SpringLayout.EAST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.SOUTH, MainWindow.del, -30, SpringLayout.SOUTH, MainWindow.pp);

        //edit
        MainWindow.layout.putConstraint(SpringLayout.EAST, MainWindow.selectGhost, -10, SpringLayout.EAST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.SOUTH, MainWindow.selectGhost, 50, SpringLayout.SOUTH, MainWindow.importButton);
        MainWindow.layout.putConstraint(SpringLayout.WEST, MainWindow.selectGhost, 0, SpringLayout.WEST, MainWindow.importFilenameScrollPane);
        MainWindow.layout.putConstraint(SpringLayout.NORTH, MainWindow.selectGhost, 5, SpringLayout.SOUTH, MainWindow.importButton);
        MainWindow.layout.putConstraint(SpringLayout.EAST, MainWindow.addButton, 0, SpringLayout.EAST, MainWindow.selectGhost);
        MainWindow.layout.putConstraint(SpringLayout.NORTH, MainWindow.addButton, 10, SpringLayout.SOUTH, MainWindow.selectGhost);
        MainWindow.layout.putConstraint(SpringLayout.WEST, MainWindow.deleteButton, 0, SpringLayout.WEST, MainWindow.selectGhost);
        MainWindow.layout.putConstraint(SpringLayout.NORTH, MainWindow.deleteButton, 10, SpringLayout.SOUTH, MainWindow.selectGhost);

        //change to
        MainWindow.layout.putConstraint(SpringLayout.SOUTH, MainWindow.changeTo, -100, SpringLayout.SOUTH, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.SOUTH, MainWindow.updatedInfo, -80, SpringLayout.SOUTH, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.SOUTH, MainWindow.nameToChange, -130, SpringLayout.SOUTH, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.EAST, MainWindow.changeTo, -10, SpringLayout.EAST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.EAST, MainWindow.updatedInfo, -10, SpringLayout.EAST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.EAST, MainWindow.nameToChange, -10, SpringLayout.EAST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.WEST, MainWindow.updatedInfo, -200, SpringLayout.EAST, MainWindow.pp);
        MainWindow.layout.putConstraint(SpringLayout.WEST, MainWindow.nameToChange, -200, SpringLayout.EAST, MainWindow.pp);


    }
}
