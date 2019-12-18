package util;

/**
 *
 * @author mathet
 */
public interface ListenableModel {
    void addModelListener(ModelListener l);
    void removeModelListener(ModelListener l);
}
