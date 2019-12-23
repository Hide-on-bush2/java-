package Core.Jood.gui;

public class Configuration {
    static Configuration ParseArgs(String[] args) {
       Configuration cfg = new Configuration();
       cfg.client = "cn.edu.sysu.jood.cli.JoodClient";
       return cfg;
    }

    public String client;
}