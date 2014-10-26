package me.ES359.SQL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by ES359 on 10/21/14.
 */
public class Main extends JavaPlugin implements Listener{

    /**
    private String host = getConfig().getString("Database.host");
    private String username = getConfig().getString("Database.username");
    private String password = getConfig().getString("Database.password");
    private String database = getConfig().getString("Database.database");
    */

    private boolean chat = getConfig().getBoolean("Table.logchat");
    private boolean commands = getConfig().getBoolean("Table.logcommands");
    private boolean playertable = getConfig().getBoolean("Table.logplayer");

    public Core sql;

    private Functions f;

    private Timestamp ts;



    public void onEnable() {
        ts = new Timestamp();
        sql = new Core(getConfig().getString("Database.host"), getConfig().getString("Database.username"), getConfig().getString("Database.password"), getConfig().getString("Database.database"));

        f = new Functions(this);

      f.createTable(chat, "CREATE TABLE IF NOT EXISTS chat (name varchar(50), UUID VARCHAR(50), chat varchar(150), stamp varchar(50) );");

       f.createTable(commands, "CREATE TABLE IF NOT EXISTS commands (name varchar(50), UUID varchar(50), command varchar(150), stamp varchar(50) );");

        f.createTable(playertable, "CREATE TABLE IF NOT EXISTS playertable (uuid VARCHAR(50), name VARCHAR(50), ip varchar(35), exp VARCHAR(50), world VARCHAR(25), location varchar(60), isOp varchar(10), whitelist varchar(10), stamp varchar(50) );");

    // f.createAnSQLTable(commands,"CREATE TABLE IF NOT EXISTS commands (name, varchar(50), UUID varchar(50), command varchar(150), data_table varchar(50) );");

       // f.createAnSQLTable(playertable, "CREATE TABLE IF NOT EXISTS playertable (uuid VARCHAR(50), name VARCHAR(50), ip varchar(35), exp VARCHAR(50), world VARCHAR(25), location varchar(60), isOp varchar(10), whitelist varchar(10) );");

        getConfig().options().copyDefaults(true);

        saveConfig();

        Bukkit.getServer().getPluginManager().registerEvents(this,this);



    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player p =event.getPlayer();


       // f.logPlayerChat(p,event.getMessage(), "INSERT INTO chat (name, UUID, chat, stamp) VALUES ('" + p.getName()+" ', '" + p.getUniqueId()+ "', '"+ event.getMessage()+ "', '" +ts.getStamp()+ "' );");

        try {
            PreparedStatement statement = sql.c.prepareStatement("INSERT INTO chat (name, UUID, chat, stamp) VALUES (?, ?, ?, ? );");

            statement.setString(1,p.getName());
            statement.setString(2,"" +p.getUniqueId());
            statement.setString(3, event.getMessage());
            statement.setString(4,""+ ts.getStamp());
            statement.execute();
            statement.close();
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE +"Logged the chat for the Player, " +p.getName());

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();

       f.logPlayer(p, "INSERT INTO playertable (uuid, name, ip, exp, world, location, isOp, whitelist) VALUES ('%uuid%', '%name%', '%ip%', '%exp%', '%world%', '%location%', '%isOp%', '%whitelist%');");


    }

    @EventHandler
    public void onC(PlayerCommandPreprocessEvent event) {

        Player p = event.getPlayer();
        String msg = event.getMessage();

        f.logCommands(p,msg, "INSERT INTO commands (uuid, name, command, stamp) VALUES ('%uuid%', '%name%', '%command%', '%date%');");

    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])   {

      if(!(sender instanceof Player)) {
          sender.sendMessage("This cannot be sent from the Console.");
        return true;
      }



        Player p =(Player)sender;

        if(cmd.getName().equalsIgnoreCase("world")) {
            if(args.length == 0) {


                p.sendMessage( ChatColor.GREEN +p.getWorld().getName());

            }else {
                 p.sendMessage("To many arguments.");
            }
        }
        return false;
    }
}
