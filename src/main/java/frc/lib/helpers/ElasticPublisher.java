package frc.lib.helpers;

import edu.wpi.first.networktables.DoubleArrayPublisher;
import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringArrayPublisher;

public class ElasticPublisher {
    
    private final DoubleArrayPublisher m_ValuePublisher;
    private final StringArrayPublisher m_LabelsPublisher;
    private final IntegerSubscriber m_SelectedSub;

    private final double m_Base;
    private final double m_Factor;
    private final int m_MaxSteps;
    private final String m_Unit;

    /**
     * Publishes a scaled value 
     * @param tableName  NT table name (shown on dashboard, e.g. "DriveCurrentLimit")
     * @param base       Starting value (e.g. 40)
     * @param factor     Multiplier per step (e.g. 2.0 for doubling)
     * @param maxSteps   Number of options to generate (e.g. 4 → 40, 80, 160, 320)
     * @param unit       Unit label appended to each option (e.g. "A", "rpm", "")
     */
    public ElasticPublisher(String tableName, double base, double factor, int maxSteps, String unit) {
        NetworkTable table = NetworkTableInstance.getDefault().getTable(tableName);

        m_ValuePublisher = table.getDoubleArrayTopic("Values").publish();
        m_LabelsPublisher = table.getStringArrayTopic("Labels").publish();
        m_SelectedSub = table.getIntegerTopic("Selected").subscribe(0);

        this.m_Base = base;
        this.m_Factor = factor;
        this.m_MaxSteps = maxSteps;
        this.m_Unit = unit;

        publishOptions();
    }

    /**
     * Doubles base value, increments by 4 steps, unitless
     * @param tableName
     * @param base
     */
    public ElasticPublisher(String tableName, double base) {
        this(tableName, base, 2.0,4, "");
    }

    /**
     * Custom factor, steps, unitless
     * @param tableName
     * @param base
     * @param factor
     * @param maxSteps
     */
    public ElasticPublisher(String tableName, double base, double factor, int maxSteps) {
        this(tableName, base, factor, maxSteps, "");
    }

    /**
     * Publishes the sorted list of values and labels to NetworkTables.
     * Called once on construction. Values are generated in ascending order.
     */
    private void publishOptions() {
        double[] values = new double[m_MaxSteps];
        String[] labels = new String[m_MaxSteps];

        for (int i = 0; i < m_MaxSteps; i++) {
            values[i] = ElasticHelpers.getValue(m_Base, m_Factor, i);
            labels[i] = String.format("%.0f%s", values[i], m_Unit);
        }

        m_ValuePublisher.set(values);
        m_LabelsPublisher.set(labels);
    }

    /**
     * Returns the currently selected value as a double.
     */
    public double getSelectedValue() {
        int index = (int) Math.max(0, Math.min(m_SelectedSub.get(), m_MaxSteps - 1));
        return ElasticHelpers.getValue(m_Base, m_Factor, index);
    }

    /**
     * Returns the currently selected value as an integer (for current limits, etc.)
     */
    public int getSelectedInt() {
        return (int) Math.round(getSelectedValue());
    }

    /**
     * Returns the selected value, clamped to a safety ceiling.
     *
     * @param maxAllowed  Hard ceiling (e.g. 80 for safe motor current)
     */
    public int getSelectedIntClamped(int maxAllowed) {
        return Math.min(getSelectedInt(), maxAllowed);
    }
}
