package ru.ifmo.se.sofia.pillow;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

class JSON {

    private static String fileToString(String input) throws PillowException {
        InputStreamReader GhostFile = null;
        try {
            GhostFile = new InputStreamReader(new FileInputStream(input));
            int data = 0;
            String s = "";
            try {
                data = GhostFile.read();
                while (data != -1) {
                    s += (char) data;
                    data = GhostFile.read();
                }
                GhostFile.close();
                return s;
            } catch (IOException e) {
                throw new PillowException("IOExeption");
            }
        } catch (FileNotFoundException ex) {
            throw new PillowException("File not found");
        }
    }

    public static void SendGhost(String input, String Zone) throws PillowException {
        String s = fileToString(input);
        InputStreamReader GhostFile = null;
        JSONParser parser = new JSONParser();
        try {
            Object mainObject = parser.parse(s);
            JSONArray garr = (JSONArray) mainObject;
            for (int i = 0; i < garr.size(); i++) {
                JSONObject per = (JSONObject) parser.parse(garr.get(i).toString());
                String name = per.get("name").toString();
                int height = Integer.parseInt(per.get("height").toString());
                String dateTimeStr = per.get("birth").toString();
                ZonedDateTime zonedDateTime = Instant.parse(dateTimeStr).atZone(ZoneId.of(Zone));
                zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Moscow"));

                Ghost perG = new Ghost(name, zonedDateTime, height);
                // FIXME: Client.send("2 " + perG.getName() + " " + perG.getAge() + " " + perG.getHeight());
                System.out.println("2 " + perG.getName() + " " + perG.getBirth() + " " + perG.getHeight());
            }
        } catch (ParseException e) {
            MainWindow.statusBar.setText("Can't parse file");
        }
    }

    public static void loadFile(ArrayList<Ghost> ghosts, String fileName, Client client, String Zone) {

        JSONParser parser = new JSONParser();
        try {
            Object mainObject = null;
            try {
                mainObject = parser.parse(new FileReader(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONArray garr = (JSONArray) mainObject;
            for (Object object : garr) {
                JSONObject data = (JSONObject) object;
                String dateTimeStr = data.get("birth").toString();
                ZonedDateTime zonedDateTime = Instant.parse(dateTimeStr).atZone(ZoneId.of(Zone));
                zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Moscow"));

                Ghost parsedGhost = new Ghost(
                        data.get("name").toString(),
                        zonedDateTime,
                        Integer.parseInt(data.get("height").toString()));
                System.out.println(parsedGhost);
                ghosts.add(parsedGhost);
                client.requestAdd(parsedGhost);
            }
        } catch (ParseException e) {
            MainWindow.statusBar.setText("Can't parse file");
        }

    }

    public static void saveGhost() {
        try {
            JSONArray arr = new JSONArray();
            for (Ghost per : (MainWindow.shownArr)) {
                JSONObject ob = new JSONObject();
                ob.put("name", per.getName());
                ob.put("birth", per.getBirth().toLocalDateTime());
                ob.put("height", per.getHeight());

                arr.add(ob);
            }
            FileOutputStream out = new FileOutputStream("in.json");
            out.write(arr.toString().getBytes());
        } catch (IOException e) {
            MainWindow.statusBar.setText("IOException while saving");
        }
    }
}
