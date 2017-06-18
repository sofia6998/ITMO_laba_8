package ru.ifmo.se.sofia.pillow;

import com.sun.rowset.CachedRowSetImpl;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

class Database {

    private long version;
    private Connection connection;
    private CachedRowSet ghosts;

    Database() throws SQLException {

        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl("jdbc:postgresql://localhost:5432/postgres");
        version = 1;

        //
            connection = ds.getConnection("sofia", "password");
            // connection.setAutoCommit(false);
            //try {
                ghosts = new CachedRowSetImpl();
                ghosts.setCommand("SELECT name, birth, height FROM ghost");
                ghosts.execute(connection);
//            } finally {
//
//            }
        /*} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ukuk");
        }*/
    }

    ArrayList<Ghost> getGhosts() throws SQLException {
        ArrayList<Ghost> result = new ArrayList<>();
        //try {
            ghosts.execute(connection);
            while (ghosts.next()) {
                String name = ghosts.getString(1);
                Timestamp birth = ghosts.getTimestamp(2);
                int height = ghosts.getInt(3);
                ZonedDateTime zdt = ZonedDateTime.ofInstant(birth.toInstant(), ZoneId.of("Europe/Moscow"));
                Ghost ghost = new Ghost(name, zdt, height);
                result.add(ghost);
            }
        /*} catch (SQLException e) {
            // TODO: Process SQLException
            e.printStackTrace();
        }*/
        System.out.println("send ghosts");
        return result;
    }

    void clear() throws SQLException {
        //try {
            String sql = "DELETE FROM ghost";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        /*} catch (SQLException e) {
            e.printStackTrace();
        }*/
        version++;
    }

    void update(String ghostName, Ghost updated) throws SQLException {
        //try {
            String sql = "UPDATE ghost SET name = ?, birth = ?, height = ? WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, updated.getName());
        ZonedDateTime zdt = updated.getBirth();

            Timestamp ts = new Timestamp(zdt.getYear(),zdt.getMonthValue(),zdt.getDayOfMonth(),zdt.getHour(),zdt.getMinute(),zdt.getSecond(),zdt.getNano());
            statement.setTimestamp(2, ts);
            statement.setInt(3, updated.getHeight());
            statement.setString(4, ghostName);
            statement.executeUpdate();
       /* } catch (SQLException e) {
            e.printStackTrace();
        }*/
        version++;
    }

    void removeLower(String ghostName) throws SQLException {
        String sql = "DELETE FROM ghost WHERE birth < (SELECT birth FROM ghost WHERE name = ?)";
        //try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ghostName);
            statement.executeUpdate();
        /*} catch (SQLException e) {
            e.printStackTrace();
        }*/
        version++;
    }

    void add(Ghost ghost) throws SQLException {

        PreparedStatement statement = connection.prepareStatement("INSERT INTO ghost (name, birth, height) VALUES (?, ?, ?)");
        statement.setString(1, ghost.getName());
        ZonedDateTime zdt = ghost.getBirth();

        Timestamp ts = new Timestamp(zdt.getYear(),zdt.getMonthValue(),zdt.getDayOfMonth(),zdt.getHour(),zdt.getMinute(),zdt.getSecond(),zdt.getNano());
        statement.setTimestamp(2, ts);
        //statement.setInt(2, ghost.getBirth());
        statement.setInt(3, ghost.getHeight());
        statement.executeUpdate();

        version++;
    }

    void delete(String ghostName) throws SQLException {
        //try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM ghost WHERE name = ?");
            statement.setString(1, ghostName);
            statement.executeUpdate();
        /*} catch (SQLException e) {
            e.printStackTrace();
        }*/
        version++;
    }

    public long getVersion() {
        return version;
    }
}
