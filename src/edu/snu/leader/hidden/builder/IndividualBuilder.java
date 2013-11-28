/*
 * COPYRIGHT
 */
package edu.snu.leader.hidden.builder;

import edu.snu.leader.hidden.SimulationState;
import edu.snu.leader.hidden.SpatialIndividual;

/**
 * IndividualBuilder
 *
 * TODO Class description
 *
 * @author Brent Eskridge
 * @version $Revision$ ($Author$)
 */
public interface IndividualBuilder
{
    /**
     * Initializes the builder
     *
     * @param simState The simulation's state
     */
    public void initialize( SimulationState simState );


    /**
     * Builds an individual
     *
     * @param index The index of the individual to build
     * @return The individual
     */
    public SpatialIndividual build( int index );
}