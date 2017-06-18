package ru.ifmo.se.sofia.pillow;

import javax.swing.tree.DefaultMutableTreeNode;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

class Tree {

    static void updateTree(ArrayList<Ghost> shownarr, ArrayList<Ghost> realarr, String Zone) {

        //MainWindow.model
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) MainWindow.model.getRoot();
        root.setUserObject(MainWindow.resourceBundle.getString("ghostKey"));
        MainWindow.model.nodeChanged(root);
        MainWindow.parent.removeAllChildren();
        //MainWindow.parent.setParent(new DefaultMutableTreeNode("ghost"));
        Commands.sortTree(shownarr, MainWindow.sort_type);

        for (Ghost aShownarr : shownarr) {
            if ((!MainWindow.rb_on) || (MainWindow.rb_on && aShownarr.getBirth().isAfter(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Europe/Moscow")).minusYears(18)))) {
                DefaultMutableTreeNode name = new DefaultMutableTreeNode(aShownarr.getName());
                DefaultMutableTreeNode age = new DefaultMutableTreeNode("age: " + aShownarr.getBirth().withZoneSameInstant(ZoneId.of(Zone)).toLocalDateTime());
                System.out.println(Zone + "   ^^   " + age.toString());
                DefaultMutableTreeNode height = new DefaultMutableTreeNode("height: " + aShownarr.getHeight());
                MainWindow.parent.add(name);
                name.add(age);
                name.add(height);
            }
        }

        MainWindow.model.reload();
        System.out.println("reloded");
        for (int i = 0; i < MainWindow.pre_tree.getRowCount(); i++) {
            MainWindow.pre_tree.expandRow(i);
        }
    }
}
