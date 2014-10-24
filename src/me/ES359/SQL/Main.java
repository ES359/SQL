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

import java.sql.SQLException;

/**
 * Created by ES359 on 10/21/14.
 */
public class Main extends JavaPlugin implements Listener{

    private String host = getConfig().getString("Database.host");
    private String username = getConfig().getString("Database.username");
    private String password = getConfig().getString("Database.password");
    private String database = getConfig().getString("Database.database");

    public Core sql;

    private Functions f;

    public Main() {}

    static public Main instance = new Main();

    static public Main getInstance() {
        return instance;

    }


    public void onEnable() {
        sql = new Core(getConfig().getString("Database.host"), getConfig().getString("Database.username"), getConfig().getString("Database.password"), getConfig().getString("Database.database"));

        try {
            sql.c.prepareStatement("CREATE TABLE IF NOT EXISTS commands (uuid VARCHAR(50), name VARCHAR(50), command VARCHAR(150)); ").executeUpdate();

        }catch (SQLException e ) {
            e.printStackTrace();
        }


        try {
            sql.c.prepareStatement("CREATE TABLE IF NOT EXISTS chat (name varchar(50), UUID VARCHAR(50), chat varchar(150) ); ").executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            sql.c.prepareStatement("CREATE TABLE IF NOT EXISTS playertable (uuid VARCHAR(50), name VARCHAR(50), ip varchar(35), exp VARCHAR(50), world VARCHAR(25), location varchar(60), isOp varchar(10), whitelist varchar(10) );").executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }


        getConfig().options().copyDefaults(true);

        saveConfig();

        Bukkit.getServer().getPluginManager().registerEvents(this,this);

        f = new Functions(this);

    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player p =event.getPlayer();


        f.logPlayerChat(p,event.getMessage(), "INSERT INTO chat (name, UUID, chat) VALUES ('%name%', '%uuid%', '%msg%' );");

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

        f.logCommands(p,msg, "INSERT INTO commands (uuid, name, command) VALUES ('%uuid%', '%name%', '%command%');");

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
