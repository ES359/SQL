package me.ES359.SQL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by ES359 on 10/21/14.
 */
public class Core  {

    public Connection c;

    public Core(String ip, String userName, String access, String db) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db + "?user=" + userName + "&password=" + access);
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Connection Error.");
        }
    }
}
