package ex5.inspector;

import ex5.util.FileLoader;

/**
 * this class handles all the inspection of the code
 */
public class Inspector {


    private final IllegalLineChecker illegalLineChecker;
    private final GlobalScopeChecker globalScopeChecker;
    private final InternalMethodsChecker internalMethodsChecker;

    /**
     * constructor for inspector class
     * @param fileLoader file loader for file
     * @see FileLoader
     * @see IllegalLineChecker
     * @see GlobalScopeChecker
     * @see InternalMethodsChecker
     */
    public Inspector(FileLoader fileLoader) {
        this.illegalLineChecker = new IllegalLineChecker(fileLoader);
        this.globalScopeChecker = new GlobalScopeChecker(fileLoader);
        this.internalMethodsChecker = new InternalMethodsChecker(fileLoader);
    }

    /**
     * this method is responsible for checking the whole code
     * @throws IllegalLineException in case of any illegal line in the program throws exception
     */
    public void checkCode() throws IllegalLineException{

        illegalLineChecker.check();

        globalScopeChecker.check();
        internalMethodsChecker.updateContextData(globalScopeChecker.getContextData());
        internalMethodsChecker.check();

    }


}
