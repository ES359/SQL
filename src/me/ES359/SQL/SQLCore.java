package me.ES359.SQL;

import java.sql.SQLException;

/**
 * Created by ES359 on 10/22/14.
 */
public class SQLCore implements createSQLTables{

    Main m = Main.getInstance();

    @Override
    public void createAnSQLTable(boolean data) {

        if(data) {
            try {
                m.sql.c.prepareStatement("CREATE TABLE IF NOT EXISTS ");
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

//http://forums.bukkit.org/threads/tutorial-extreme-beyond-reflection-asm-replacing-loaded-classes.99376/

    /**
     *  public void createTableChat(boolean chat) {

     if(chat) {
     try {
     m.sql.c.prepareStatement("CREATE TABLE IF NOT EXISTS chat (name varchar(50), UUID VARCHAR(50), chat varchar(150) ); ").executeUpdate();
     }catch (SQLException e) {
     e.printStackTrace();
     }
     }
     }

     public void createTableCommands(boolean commands) {

     if(commands) {
     try {
     m.sql.c.prepareStatement("CREATE TABLE IF NOT EXISTS commands (name, varchar(50), UUID varchar(50), command(150) );").executeUpdate();
     }catch (SQLException e) {
     e.printStackTrace();
     }
     }
     }

     public void createTablePlayer(boolean playerdata) {

     if(playerdata) {

     try {
     m.sql.c.prepareStatement("CREATE TABLE IF NOT EXISTS playertable (uuid VARCHAR(50), name VARCHAR(50), ip varchar(35), exp VARCHAR(50), world VARCHAR(25), location varchar(60), isOp varchar(10), whitelist varchar(10) );").executeUpdate();

     }catch (SQLException e) {
     e.printStackTrace();
     }
     }
     }
     */

