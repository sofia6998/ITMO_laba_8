package ru.ifmo.se.sofia.pillow;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.zip.ZipInputStream;

import static javax.swing.JOptionPane.YES_OPTION;

class MainWindow extends JFrame {
    static ArrayList<Ghost> realArr;
    static ArrayList<Ghost> shownArr;
    static int sort_type = 1;
    static JTree pre_tree;
    static JScrollPane tree;
    static JLabel sortL;
    static JList sortList;
    static JRadioButton rb;
    static JButton sortB;
    static JButton del;
    static JButton removeLowerButton;
    static JScrollPane importFilenameScrollPane;
    static JButton importButton;
    static JPanel pp;
    static SpringLayout layout;
    public static JScrollPane name;
    static DefaultTreeModel model;
    static DefaultMutableTreeNode parent;
    static boolean rb_on = false;
    static JTextArea selectGhost;
    static JButton addButton;
    static JButton deleteButton;
    static JTextArea nameToChange;
    static JTextArea updatedInfo;
    static JButton changeTo;
    static JLabel statusBar;
    static JList languageList;
    static JButton language;
    private static JEditorPane importFilename;
    private static JTextArea pre_name;
    //private final Client client;
    static String Zone;
    static ResourceBundle resourceBundle;

    MainWindow(Client client) {
        super("Ghosts");
        Zone = ZoneId.systemDefault().toString();
        resourceBundle =
                ResourceBundle.getBundle("resource", new Locale("ru"));
        //this.client = client;
        realArr = new ArrayList<>();
        shownArr = new ArrayList<>();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 490);
        pp = new JPanel();
        layout = new SpringLayout();
        pp.setLayout(layout);

        parent = new DefaultMutableTreeNode(resourceBundle.getString("ghostKey"), true);
        model = new DefaultTreeModel(parent);
        pre_tree = new JTree(MainWindow.model);
        pre_tree.setEditable(false);
        MainWindow.tree = new JScrollPane(MainWindow.pre_tree);
        Tree.updateTree(shownArr, realArr, Zone);

        statusBar = new JLabel(resourceBundle.getString("problemsKey"));

        changeTo = new JButton(resourceBundle.getString("changeToKey"));
        nameToChange = new JTextArea();
        updatedInfo = new JTextArea();
        changeTo.addActionListener(e -> {
            String ghostInfo = updatedInfo.getText().trim();
            Scanner scanner = new Scanner(ghostInfo);
            scanner.useDelimiter(" ");

            try {
                String name = scanner.next();
                ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.of(Zone));
                zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Moscow"));
                //todo: считывание даты
                int height = scanner.nextInt();
                Ghost updated = new Ghost(name, zonedDateTime, height);

                if (updatedInfo.getText().split(" ").length == 3) {
                    client.requestUpdate(nameToChange.getText(), updated);
                    if (client.receive()) {
                        Commands.change(nameToChange.getText(), updated);
                        Tree.updateTree(shownArr, realArr, Zone);
                        statusBar.setText("");
                    }
                    statusBar.setText("");
                } else {
                    new PillowException(resourceBundle.getString("wrongDataKey"));
                }
            }catch (RuntimeException e3){
                new PillowException(resourceBundle.getString("wrongDataKey"));
            }
        });


        sortL = new JLabel("Sort by:");
        sortList = new JList(new String[]{
                resourceBundle.getString("nameKey"),
                resourceBundle.getString("ageKey"),
                resourceBundle.getString("heightKey")});
        sortList.setSelectedIndex(1);
        rb = new JRadioButton(resourceBundle.getString("adultKey"));
        sortB = new JButton(resourceBundle.getString("sortKey"));
        sortB.addActionListener(actionEvent -> {
            rb_on = rb.isSelected();
            sort_type = sortList.getSelectedIndex();
            Tree.updateTree(shownArr, realArr, Zone);
        });


        importFilename = new JEditorPane();
        importFilenameScrollPane = new JScrollPane(importFilename);
        importButton = new JButton(resourceBundle.getString("importKey"));
        importButton.addActionListener(actionEvent -> {
            String fname = importFilename.getText().split("\n")[0];
            try {
                Commands.imp(fname, client);
                Tree.updateTree(shownArr, realArr, Zone);
            } catch (Exception ex) {

            }
        });

        pre_name = new JTextArea();
        name = new JScrollPane(pre_name);
        removeLowerButton = new JButton(resourceBundle.getString("removeLowerKey"));
        removeLowerButton.addActionListener(actionEvent -> {
            final String ghostName = pre_name.getText();
            new Thread(() -> {
                client.requestRemoveLower(ghostName);
                if (client.receive()) {
                    Commands.removeLower(ghostName);
                    Tree.updateTree(shownArr, realArr, Zone);
                    statusBar.setText("");
                }
            }).run();
        });

        del = new JButton(resourceBundle.getString("deleteAllKey"));
        del.addActionListener(actionEvent -> {
            int shallDelete = JOptionPane.showConfirmDialog(
                    this,
                    resourceBundle.getString("sureMsgKey"),
                    resourceBundle.getString("confirmKey"),
                    JOptionPane.YES_NO_OPTION);
            if (shallDelete == YES_OPTION) {
                client.requestClear();
                if (client.receive()) {
                    Commands.clear();
                    Tree.updateTree(shownArr, realArr, Zone);
                    statusBar.setText(resourceBundle.getString("allDeletedKey"));
                }
            }
        });

        selectGhost = new JTextArea();
        addButton = new JButton(resourceBundle.getString("addKey"));
        deleteButton = new JButton(resourceBundle.getString("deleteKey"));
        deleteButton.addActionListener(actionEvent -> {
            final String ghostName = selectGhost.getText();
            new Thread(() -> {
                client.requestDelete(ghostName);
                if (client.receive()) {
                    Commands.delete(ghostName);
                    Tree.updateTree(shownArr, realArr, Zone);
                    statusBar.setText("");
                }
            }).run();
        });

        addButton.addActionListener(actionEvent -> {
            String ghostInfo = selectGhost.getText().trim();
            Scanner scanner = new Scanner(ghostInfo);
            scanner.useDelimiter(" ");
            try {
                String name = scanner.next();
                ZonedDateTime zonedDateTime= Instant.now().atZone(ZoneId.of(Zone));
                zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Moscow"));
                int height = scanner.nextInt();
                Ghost ghost = new Ghost(name, zonedDateTime , height);
                client.requestAdd(ghost);
                if (client.receive()) {
                    Commands.add(ghost);
                    Tree.updateTree(shownArr, realArr, Zone);
                    statusBar.setText("");
                } else {
                    statusBar.setText(resourceBundle.getString("wrongDataKey"));
                }
            } catch (RuntimeException e){
                System.out.println(e.getMessage());
                statusBar.setText(resourceBundle.getString("wrongDataKey"));
            }

        });

        language = new JButton(resourceBundle.getString("goKey"));
        languageList = new JList(new String[]{
                resourceBundle.getString("ruKey"),
                resourceBundle.getString("fiKey"),
                resourceBundle.getString("swKey"),
                resourceBundle.getString("spKey")});
        sortList.setSelectedIndex(1);
        language.addActionListener(e -> {
            String tag ="";
            switch (languageList.getSelectedIndex()){
                case 1: Zone = "GMT+3";
                        tag = "ru";
                        break;
                case 2: Zone = "GMT+2";
                        tag = "fi";
                        break;
                case 3: Zone = "GMT+1";
                        tag = "sw";
                        break;
                case 4: Zone = "GMT-6";
                        tag = "sp";
                        break;
            }

            resourceBundle =
                    ResourceBundle.getBundle("resource", new Locale(tag));
            changeLanguage();

        });

        Layouts.create();
        setContentPane(pp);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                Commands.save();
            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {
                Commands.save();
            }
        });

        JSON.loadFile(realArr, "in.json", client, Zone);
        JSON.loadFile(shownArr, "in.json", client, Zone);
        Tree.updateTree(shownArr, realArr, Zone);
    }
    void changeLanguage(){
        sortB.setText(resourceBundle.getString("sortKey"));
        del.setText(resourceBundle.getString("deleteKey"));
        sortList.setListData(new String[]{
                resourceBundle.getString("nameKey"),
                resourceBundle.getString("ageKey"),
                resourceBundle.getString("heightKey")
        });
        removeLowerButton.setText(resourceBundle.getString("removeLowerKey"));
        importButton.setText(resourceBundle.getString("importKey"));
        addButton.setText(resourceBundle.getString("addKey"));
        deleteButton.setText(resourceBundle.getString("deleteKey"));
        changeTo.setText(resourceBundle.getString("changeToKey"));
        statusBar.setText("");
        languageList.setListData(new String[]{
                resourceBundle.getString("ruKey"),
                resourceBundle.getString("fiKey"),
                resourceBundle.getString("swKey"),
                resourceBundle.getString("spKey")
        });
        language.setText(resourceBundle.getString("goKey"));
        //parent.setParent(new DefaultMutableTreeNode(resourceBundle.getString("ghostKey"), true));
       // parent = new DefaultMutableTreeNode(resourceBundle.getString("ghostKey"), true);
        //model = new DefaultTreeModel(parent);
        Tree.updateTree(shownArr, realArr, Zone);
    }
}
