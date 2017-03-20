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

import edu.snu.leader.hidden.SimulationState;
import edu.snu.leader.hidden.SpatialIndividual;
import edu.snu.leader.hidden.Task;

/**
 * ExperienceUpdator
 *
 * TODO Class description
 *
 * @author Brent Eskridge
 * @version $Revision$ ($Author$)
 */
public interface PersonalityCalculator
{
    /**
     * Initializes the calculator
     *
     * @param simState The simulation's state
     */
    public void initialize( SimulationState simState );

    /**
     * Calculate the new personality value
     *
     * @param individual The individual's current personality
     * @param updateType The type of update being applied
     * @param followers The number of followers in the initiation
     * @return The updated personality value
     */
    @Deprecated
    public float calculatePersonality( SpatialIndividual individual,
            PersonalityUpdateType updateType,
            int followers );
    
    /**
     * Calculate the personality trait value(s)
     *
     * @param individual The individual's current personality
     * @param updateType The type of update being applied
     * @param task The current task
     */
    public void updateTraits( SpatialIndividual individual,
            PersonalityUpdateType updateType,
            Task currentTask );
}
