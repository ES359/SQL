package me.ES359.SQL;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by ES359 on 10/21/14.
 */
public class Functions {

    public Main main;

    public Functions(Main instance) {
        main = instance;
    }

    Timestamp ts = new Timestamp();

    /**
     *
     * @param data Takes a configuration file param to see if creation of table is wanted.
     * @param sql Define parameters for your table using SQL.
     */

    public void createTable(boolean instance, String sql) {
        if(instance) {
            try {
                main.sql.c.prepareStatement(sql).executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *
     * @param p Takes a Player object as a Parameter.
     * @param msg Message to log.
     * @param sql Define parameters for your table using SQL.
     */
    public void logPlayerChat(Player p,String msg, String sql){

        String name = p.getName();
        String ip = "" +p.getAddress();
        UUID uuid = p.getUniqueId();

        sql = sql.replaceAll("%name%", p.getName());
        sql = sql.replaceAll("%ip%",""+ p.getAddress());
        sql = sql.replaceAll("%uuid%", "" +p.getUniqueId());
        sql = sql.replaceAll("%msg%", msg);
        sql = sql.replaceAll("%date%", "" +ts.stamp);
        try {
          main.sql.c.prepareStatement(sql).executeUpdate();
            Bukkit.getServer().getConsoleSender().sendMessage("Logged the chat for " +p.getName());
            //LogToConsole(sql_prefix +" &7Logged the chat for &bplayer, &8"+name);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param p Takes a Player object as a Parameter.
     * @param sql Define parameters for your table using SQL.
     */
    @SuppressWarnings("Functions: whitelist,isOp,location,world,exp,name,uuid")
    public void logPlayer(Player p, String sql) {
        String name = p.getName();
        String ip = "" +p.getAddress();
        UUID uuid = p.getUniqueId();
        String world = p.getWorld().getName();
        String exp =""+ p.getExpToLevel();
        String location = "X: " +p.getLocation().getBlockX() + " Y: " + p.getLocation().getY() + " Z: " + p.getLocation().getBlockZ();
        boolean whitelist = p.isWhitelisted();
        boolean isOp = p.isOp();

        sql = sql.replaceAll("%date%", "" +ts.stamp);
        sql = sql.replaceAll("%whitelist%","" + whitelist);
        sql = sql.replaceAll("%isOp%","" + isOp);
        sql = sql.replaceAll("%location%", location);
        sql = sql.replaceAll("%world%", ""+p.getWorld().getName());
        sql = sql.replaceAll("%exp%", "" +p.getExpToLevel());
        sql = sql.replaceAll("%name%", p.getName());
        sql = sql.replaceAll("%ip%",""+ p.getAddress());
        sql = sql.replaceAll("%uuid%", "" +p.getUniqueId());
        try{
            main.sql.c.prepareStatement(sql).executeUpdate();
            Bukkit.getServer().getConsoleSender().sendMessage("Logged the player, " +name );
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param p Takes a Player object as a Parameter.
     * @param cmds Command to be registered.
     * @param sql Define parameters for your table using SQL.
     */
    public void logCommands(Player p, String cmds, String sql) {

        String name = p.getName();
        String ip = "" +p.getAddress();
        UUID uuid = p.getUniqueId();
        String world = p.getWorld().getName();
        String exp =""+ p.getExpToLevel();
        String location = "X: " +p.getLocation().getBlockX() + " Y: " + p.getLocation().getY() + " Z: " + p.getLocation().getBlockZ();
        boolean whitelist = p.isWhitelisted();
        boolean isOp = p.isOp();

        sql = sql.replaceAll("%date%", "" +ts.stamp);
        sql = sql.replaceAll("%name%", p.getName());
        sql = sql.replaceAll("%ip%",""+ p.getAddress());
        sql = sql.replaceAll("%uuid%", "" +p.getUniqueId());
        sql = sql.replaceAll("%command%", cmds);

        try {

            main.sql.c.prepareStatement(sql).executeUpdate();

            Bukkit.getServer().getConsoleSender().sendMessage("Logged the player, " +name);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
