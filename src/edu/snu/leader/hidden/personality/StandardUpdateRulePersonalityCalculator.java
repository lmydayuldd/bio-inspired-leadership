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
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import edu.snu.leader.hidden.SimulationState;
import edu.snu.leader.hidden.SpatialIndividual;
import edu.snu.leader.hidden.Task;

import java.util.Properties;


/**
 * StandardUpdateRulePersonalityCalculator
 *
 * TODO Class description
 *
 * @author Brent Eskridge
 * @version $Revision$ ($Author$)
 */
public class StandardUpdateRulePersonalityCalculator
        implements PersonalityCalculator
{
    /** Our logger */
    private static final Logger _LOG = Logger.getLogger(
            StandardUpdateRulePersonalityCalculator.class.getName() );



    /** Key for the discount */
    private static final String _DISCOUNT_KEY = "personality-discount";

    /** Key for the minimum personality value */
    private static final String _MIN_PERSONALITY_KEY = "min-personality";

    /** Key for the maximum personality value */
    private static final String _MAX_PERSONALITY_KEY = "max-personality";

    /** Key for the true winner discount */
    private static final String _TRUE_WINNER_DISCOUNT_KEY = "true-winner-discount";

    /** Key for the true loser discount */
    private static final String _TRUE_LOSER_DISCOUNT_KEY = "true-loser-discount";

    /** Key for the flag indicating that true winner effects are active */
    private static final String _TRUE_WINNER_EFFECTS_ACTIVE_KEY = "true-winner-effects-active";

    /** Key for the flag indicating that true loser effects are active */
    private static final String _TRUE_LOSER_EFFECTS_ACTIVE_KEY = "true-loser-effects-active";

    /** Key for the winner reward */
    private static final String _WINNER_REWARD_KEY = "winner-reward";

    /** Key for the loser penalty */
    private static final String _LOSER_PENALTY_KEY = "loser-penalty";


    /** The update rule's discount */
    protected float _discount = 0.0f;

    /** The update rule's true winner discount */
    protected float _trueWinnerDiscount = 0.0f;

    /** The update rule's true loser discount */
    protected float _trueLoserDiscount = 0.0f;

    /** The update rule's true winner reward */
    protected float _winnerReward = 0.0f;

    /** The update rule's true loser reward */
    protected float _loserPenalty = 0.0f;

    /** Flag indicating whether or not true winner effects are active */
    protected boolean _trueWinnerEffectActive = false;

    /** Flag indicating whether or not true loser effects are active */
    protected boolean _trueLoserEffectActive = false;

    /** The minimum allowable personality value */
    protected float _minPersonality = 0.0f;

    /** The maximum allowable personality value */
    protected float _maxPersonality = 1.0f;


    /**
     * Initializes the updater
     *
     * @param simState The simulation's state
     * @see edu.snu.leader.hidden.personality.PersonalityCalculator#initialize(edu.snu.leader.hidden.SimulationState)
     */
    @Override
    public void initialize( SimulationState simState )
    {
        _LOG.trace( "Entering initialize( simState )" );

        // Get the properties
        Properties props = simState.getProps();

        // Get the discount value
        String discountStr = props.getProperty( _DISCOUNT_KEY );
        Validate.notEmpty( discountStr,
                "Personality discount (key="
                + _DISCOUNT_KEY
                + ") may not be empty" );
        _discount = Float.parseFloat( discountStr );
        _LOG.info( "Using _discount=[" + _discount + "]" );

        // Get the true winner discount value
        String trueWinnerDiscountStr = props.getProperty( _TRUE_WINNER_DISCOUNT_KEY );
        if( null != trueWinnerDiscountStr )
        {
            _trueWinnerDiscount = Float.parseFloat( trueWinnerDiscountStr );
        }
        else
        {
            _trueWinnerDiscount = _discount;
        }
        _LOG.info( "Using _trueWinnerDiscount=[" + _trueWinnerDiscount + "]" );

        // Get the true loser discount value
        String trueLoserDiscountStr = props.getProperty( _TRUE_LOSER_DISCOUNT_KEY );
        if( null != trueLoserDiscountStr )
        {
            _trueLoserDiscount = Float.parseFloat( trueLoserDiscountStr );
        }
        else
        {
            _trueLoserDiscount = _discount;
        }
        _LOG.info( "Using _trueLoserDiscount=[" + _trueLoserDiscount + "]" );

        // Get the winner reward
        String winnerRewardStr = props.getProperty( _WINNER_REWARD_KEY );
        Validate.notEmpty( winnerRewardStr,
                "Winner reward (key=["
                + _WINNER_REWARD_KEY
                + "]) may not be empty" );
        _winnerReward = Float.parseFloat( winnerRewardStr );
        _LOG.info( "Using _winnerReward=[" + _winnerReward + "]" );

        // Get the loser penalty
        String loserPenaltyStr = props.getProperty( _LOSER_PENALTY_KEY );
        Validate.notEmpty( loserPenaltyStr,
                "Loser penalty (key=["
                + _LOSER_PENALTY_KEY
                + "]) may not be empty" );
        _loserPenalty = Float.parseFloat( loserPenaltyStr );
        _LOG.info( "Using _loserPenalty=[" + _loserPenalty + "]" );

        // Get the true winner effect flag
        String trueWinnerEffectStr = props.getProperty( _TRUE_WINNER_EFFECTS_ACTIVE_KEY );
        Validate.notEmpty( trueWinnerEffectStr,
                "True winner effects active flag (key=["
                        + _TRUE_WINNER_EFFECTS_ACTIVE_KEY
                        + "]) may not be empty" );
        _trueWinnerEffectActive = Boolean.parseBoolean( trueWinnerEffectStr );
        _LOG.info( "Using _trueWinnerEffectActive=["
                + _trueWinnerEffectActive
                + "]" );

        // Get the true loser effect flag
        String trueLoserEffectStr = props.getProperty( _TRUE_LOSER_EFFECTS_ACTIVE_KEY );
        Validate.notEmpty( trueLoserEffectStr,
                "True loser effects active flag (key=["
                        + _TRUE_LOSER_EFFECTS_ACTIVE_KEY
                        + "]) may not be empty" );
        _trueLoserEffectActive = Boolean.parseBoolean( trueLoserEffectStr );
        _LOG.info( "Using _trueLoserEffectActive=["
                + _trueLoserEffectActive
                + "]" );


        // Get the min personality
        String minPersonalityStr = props.getProperty( _MIN_PERSONALITY_KEY );
        Validate.notEmpty( minPersonalityStr,
                "Minimum personality value (key="
                + _MIN_PERSONALITY_KEY
                + ") may not be empty" );
        _minPersonality = Float.parseFloat( minPersonalityStr );
        _LOG.info( "Using _minPersonality=[" + _minPersonality + "]" );

        // Get the max personality
        String maxPersonalityStr = props.getProperty( _MAX_PERSONALITY_KEY );
        Validate.notEmpty( maxPersonalityStr,
                "Maximum personality value (key="
                + _MAX_PERSONALITY_KEY
                + ") may not be empty" );
        _maxPersonality = Float.parseFloat( maxPersonalityStr );
        _LOG.info( "Using _maxPersonality=[" + _maxPersonality + "]" );


        _LOG.trace( "Leaving initialize( simState )" );
    }

    /**
     * Calculate the new personality value
     *
     * @param currentPersonality The individual's current personality
     * @param updateType The type of update being applied
     * @param followers The number of followers in the initiation
     * @return The updated personality value
     * @see edu.snu.leader.hidden.personality.PersonalityCalculator#calculatePersonality(SpatialIndividual, PersonalityUpdateType, int)
     */
    @Override
    public float calculatePersonality( SpatialIndividual individual,
            PersonalityUpdateType updateType,
            int followers )
    {
        float result = 0.0f;
        float discount = 0.0f;

        // What type was the update?
        boolean valid = false;
        if( PersonalityUpdateType.TRUE_WINNER.equals( updateType ) )
        {
            // True winner
            if( _trueWinnerEffectActive )
            {
                result = calculateTrueWinnerResult( individual, followers );
                discount = calculateTrueWinnerDiscount( individual, followers );
                valid = true;
            }
        }
        else if( PersonalityUpdateType.TRUE_LOSER.equals( updateType ) )
        {
            // True loser
            if( _trueLoserEffectActive )
            {
                result = calculateTrueLoserResult( individual, followers );;
                discount = _trueLoserDiscount;
                valid = true;
            }
        }

        // Calculate the new personality using a standard update rule
        float newPersonality = individual.getPersonality();
        if( valid )
        {
            newPersonality = ( (1.0f - discount) * individual.getPersonality() )
                    + ( discount * result );

            newPersonality = ensureValidPersonality( newPersonality );
        }


        return newPersonality;
    }
    
    /**
     * Calculate the personality trait value(s)
     *
     * @param individual The individual's current personality
     * @param updateType The type of update being applied
     * @param task The current task
     * @see edu.snu.leader.hidden.personality.PersonalityCalculator#updateTraits(edu.snu.leader.hidden.SpatialIndividual, edu.snu.leader.hidden.personality.PersonalityUpdateType, edu.snu.leader.hidden.Task)
     */
    @Override
    public void updateTraits( SpatialIndividual individual,
            PersonalityUpdateType updateType,
            Task currentTask )
    {
        // Do nothing
    }


    /**
     * Calculates the true winner result for the given individual and number
     * of followers
     *
     * @param individual The winning, initiating individual
     * @param followers The number of followers
     * @return The true winner result
     */
    protected float calculateTrueWinnerResult( SpatialIndividual individual,
            int followers )
    {
        return _winnerReward;
    }

    /**
     * Calculates the true winner discount for the given individual and number
     * of followers
     *
     * @param individual The winning, initiating individual
     * @param followers The number of followers
     * @return The true winner discount
     */
    protected float calculateTrueWinnerDiscount( SpatialIndividual individual,
            int followers )
    {
        return _trueWinnerDiscount;
    }

    /**
     * Calculates the true loser result for the given individual and number
     * of followers
     *
     * @param individual The loser, initiating individual
     * @param followers The number of followers
     * @return The true loser result
     */
    protected float calculateTrueLoserResult( SpatialIndividual individual,
            int followers )
    {
        return _loserPenalty;
    }


    /**
     * Ensure that the personality value is within bounds
     *
     * @param personality A calculated personality value
     * @return The new personality value within bounds
     */
    protected float ensureValidPersonality( float personality )
    {
        // Is it under the minimum?
        if( _minPersonality > personality )
        {
            personality = _minPersonality;
        }
        // Is it over the maximum?
        else if( _maxPersonality < personality )
        {
            personality = _maxPersonality;
        }

        return personality;
    }
}
