/*
 *  The Bio-inspired Leadership Toolkit is a set of tools used to
 *  simulate the emergence of leaders in multi-agent systems.
 *  Copyright (C) 2014 Southern Nazarene University
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.snu.leader.hidden.personality;

// Imports
import org.apache.log4j.Logger;
import edu.snu.leader.hidden.SimulationState;
import edu.snu.leader.hidden.SpatialIndividual;


/**
 * ConstantDecay
 * 
 * @author Jeremy Acre
 * @version $Revision$ ($Author$)
 */
public class ConstantDecay extends AbstractPersonalityDecayCalculator
{
    /** Our logger */
    private static final Logger _LOG = Logger.getLogger( ConstantDecay.class.getName() );
    
    /** The decay rate */
    private float _decayRate = 0.0f;
    
    
    /**
     * Initialize the decay calculator
     * 
     * @param simState The simulation's state
     * @see edu.snu.leader.hidden.personality.AbstractPersonalityDecayCalculator#initialize(edu.snu.leader.hidden.SimulationState)
     */
    @Override
    public void initialize( SimulationState simState )
    {
        // Call the superclass implementation
        super.initialize( simState );
        
        // Calculate the constant decay rate using the decay time
        _decayRate = 1.0f / _decayTime;
    }



    /**
     * Calculates the decayed personality of the specified individual
     *
     * @param ind The individual whose personality will decay
     * @return The decayed personality
     */
    @Override
    public float calculateDecayedPersonality( SpatialIndividual ind )
    {
        // The individual's new personality
        float newPersonality = ind.getPersonality();

        // If the current personality is greater than the initial personality
        if( ind.getPersonality() > ind.getInitialPersonality() )
        {
            // Calculate the new personality
            newPersonality = ind.getPersonalityAfterLastInitiation()
                    - ( ( _simState.getSimIndex() - ind.getLastInitiationAttempt() ) 
                      * _decayRate );
            _LOG.debug( "> personalityAfterLast=["
                    + ind.getPersonalityAfterLastInitiation()
                    + "] decayRate=["
                    + _decayRate
                    + "] timeDiff=["
                    + ( _simState.getSimIndex() - ind.getLastInitiationAttempt() )
                    + "] newPersonality=["
                    + newPersonality
                    + "]" );
        }

        // Otherwise if the current personality is less than the initial
        // personality.
        else if( ind.getPersonality() < ind.getInitialPersonality() )
        {
            // Calculate the new personality
            newPersonality = ind.getPersonalityAfterLastInitiation()
                    + ( ( _simState.getSimIndex() - ind.getLastInitiationAttempt() ) 
                      * _decayRate );
            _LOG.debug( "< personalityAfterLast=["
                    + ind.getPersonalityAfterLastInitiation()
                    + "] decayRate=["
                    + _decayRate
                    + "] timeDiff=["
                    + ( _simState.getSimIndex() - ind.getLastInitiationAttempt() )
                    + "] newPersonality=["
                    + newPersonality
                    + "]" );
        }

        // Return new personality
        return newPersonality;
    }

}
