package frc.lib.helpers;

public class ElasticHelpers {
    
    /**
     * Returns an exponentially scaled value (double)
     * @param baseValue starting value (e.g. 40)
     * @param factor multiplier (e.g. 2 for doubling, 3 for tripling)
     * @param n step index (0,1,2,...)
     */
    public static double getValue(double baseValue, double factor, int n) {
        return baseValue * Math.pow(factor , n);
    }

    /**
     * Returns an exponentially scaled value (int)
     * @param baseValue starting value (e.g. 40)
     * @param factor multiplier (e.g. 2 for doubling, 3 for tripling)
     * @param n step index (0,1,2,...)
     */
    public static int getInt(double baseValue, double factor, int n) {
        return (int) Math.round(getValue(baseValue, factor, n));
    }

    /**
     * Returns a exponentially scaled value (int) with a roof
     * @param baseValue  Starting value
     * @param factor     Multiplier per step
     * @param n          Step index
     * @param maxAllowed Hard ceiling (e.g. 80 for safe motor ops)
     * @return           Scaled value, clamped to maxAllowed
     */
    public static int getIntClamped(double baseValue, double factor, int n, int maxAllowed) {
        return Math.min(getInt(baseValue, factor, n), maxAllowed);
    }
}
