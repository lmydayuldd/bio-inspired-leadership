/*
 * COPYRIGHT
 */
package edu.snu.leader.hidden.builder;

// Imports
import java.awt.geom.Point2D;

import edu.snu.leader.hidden.SpatialIndividual;


/**
 * DefaultIndividualBuilder
 *
 * TODO Class description
 *
 * @author Brent Eskridge
 * @version $Revision$ ($Author$)
 */
public class DefaultIndividualBuilder extends AbstractIndividualBuilder
{

    /**
     * Builds an individual
     *
     * @param index The index of the individual to build
     * @return The individual
     * @see edu.snu.leader.hidden.builder.IndividualBuilder#build(int)
     */
    @Override
    public SpatialIndividual build( int index )
    {
        // Create a valid location
        Point2D location = createValidLocation( index );

        // Create the individual
        SpatialIndividual ind = new SpatialIndividual(
                generateUniqueIndividualID( index ),
                location,
                DEFAULT_PERSONALITY,
                DEFAULT_ASSERTIVENESS,
                DEFAULT_PREFERRED_DIR,
                DEFAULT_RAW_CONFLICT,
                DEFAULT_DESCRIBE_INITIATION_HISTORY );

        return ind;
    }

}