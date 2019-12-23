package Core.Jood.gui;

import Core.Jood.core.Jood;

/**
 * Entrypoint of Jood Application
 */
public interface Client {
    /**
     * Retrieve Jood Core instance. It is a useless API
     * @return {@link Jood} Jood instance of client.
     */
    public Jood Core();
    /**
     * Init Client from command line options.
     * @param cfg {@link Configuration} options
     * @throws InitException throw exceptions if init client failed.
     */
    public void Init(Configuration cfg) throws InitException;
    /**
     * Run Client of Jood.
     */
    public void Run();
}