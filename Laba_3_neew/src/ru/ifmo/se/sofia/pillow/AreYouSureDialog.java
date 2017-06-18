package ru.ifmo.se.sofia.pillow;

import javax.swing.*;

class AreYouSureDialog extends JFrame {
    AreYouSureDialog() {
        super("Delete");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(200, 150);
        JPanel pp = new JPanel();
        SpringLayout layout = new SpringLayout();
        pp.setLayout(layout);
        JLabel text = new JLabel("Sure?");
        final JList list = new JList(new String[]{"yes", "no"});
        JButton ok = new JButton("ok");

        ok.addActionListener(actionEvent -> {
            if (list.getSelectedValue().equals("yes")) {
                // FIXME: Client.send("4");
                /*
                if (Client.receive()) {
                    Commands.clear();
                    Tree.updateTree(MainWindow.shownArr, MainWindow.realArr);
                }
                */
            }
            close();
        });
        pp.add(text);
        pp.add(list);
        pp.add(ok);

        layout.putConstraint(SpringLayout.WEST, text, 10, SpringLayout.WEST, pp);
        layout.putConstraint(SpringLayout.EAST, text, -10, SpringLayout.EAST, pp);

        layout.putConstraint(SpringLayout.NORTH, text, 5, SpringLayout.NORTH, pp);

        layout.putConstraint(SpringLayout.WEST, list, 10, SpringLayout.WEST, pp);
        layout.putConstraint(SpringLayout.EAST, list, -10, SpringLayout.EAST, pp);
        layout.putConstraint(SpringLayout.NORTH, list, 10, SpringLayout.SOUTH, text);

        layout.putConstraint(SpringLayout.WEST, ok, 10, SpringLayout.WEST, pp);
        layout.putConstraint(SpringLayout.EAST, ok, -10, SpringLayout.EAST, pp);
        layout.putConstraint(SpringLayout.NORTH, ok, 5, SpringLayout.SOUTH, list);

        setContentPane(pp);
    }

    private void close() {
        this.setVisible(false);
    }

}

