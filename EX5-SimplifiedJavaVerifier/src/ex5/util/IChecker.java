package ex5.util;

import ex5.inspector.IllegalLineException;

/**
 * interface for checker classes
 */
public interface IChecker {

    /**
     * does a checks on a file.
     *
     * @throws IllegalLineException throws an exception in case of an illegal line been found in
     * check
     */
    public void check() throws IllegalLineException;
}
