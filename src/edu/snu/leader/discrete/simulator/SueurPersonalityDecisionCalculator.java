package edu.snu.leader.discrete.simulator;

import java.util.List;

import org.apache.commons.lang.Validate;

import edu.snu.leader.discrete.behavior.Decision;
import edu.snu.leader.discrete.behavior.Decision.DecisionType;
import edu.snu.leader.discrete.utils.Reporter;


public class SueurPersonalityDecisionCalculator implements
        DecisionProbabilityCalculator
{

    /** The simulation state */
    private SimulationState _simState = null;

    /** The intrinsic probability to initiate */
    private double _alpha = 0;

    /** The intrinsic probability to cancel */
    private double _alphaC = 0;

    /** Mimetic coefficient */
    private double _beta = 0;

    /** Inverse mimetic coefficient */
    private double _betaC = 0;

    /** Agents sensitivity to the system */
    private double _q = 0;

    /** A threshold */
    private int _S = 0;

    @Override
    public void initialize( SimulationState simState )
    {
        _simState = simState;

        String alpha = _simState.getProperties().getProperty( "alpha" );
        Validate.notEmpty( alpha, "Alpha may not be empty" );
        _alpha = Double.parseDouble( alpha );

        String alphaC = _simState.getProperties().getProperty( "alpha-c" );
        Validate.notEmpty( alphaC, "Alpha-c may not be empty" );
        _alphaC = Double.parseDouble( alphaC );

        String beta = _simState.getProperties().getProperty( "beta" );
        Validate.notEmpty( beta, "Beta may not be empty" );
        _beta = Double.parseDouble( beta );

        String betaC = _simState.getProperties().getProperty( "beta-c" );
        Validate.notEmpty( betaC, "Beta-c may not be empty" );
        _betaC = Double.parseDouble( betaC );

        String q = _simState.getProperties().getProperty( "q" );
        Validate.notEmpty( q, "q may not be empty" );
        _q = Double.parseDouble( q );

        String S = _simState.getProperties().getProperty( "S" );
        Validate.notEmpty( S, "S may not be empty" );
        _S = Integer.parseInt( S );

        String cancellationThreshold = _simState.getProperties().getProperty(
                "cancellation-threshold" );
        Validate.notEmpty( cancellationThreshold,
                "Use cancellation threshold may not be empty" );

        // add sueur info to root directory path
        _simState.setRootDirectory( Reporter.ROOT_DIRECTORY + "q=" + _q + "_"
                + "S=" + _S );

    }

    @Override
    public void calcInitiateProb( Decision decision )
    {
        decision.setProbability( kValue( decision ) * _alpha );
    }

    @Override
    public void calcFollowProb( Decision decision )
    {
        Agent agent = decision.getAgent();
        Group group = decision.getLeader().getGroup();
        // probability to join this group
        double lambda = 0.0;

        // the number of agents currently in this group
        int X = 1;

        // calculate observed X value
        List<Agent> neighbors = agent.getNearestNeighbors();
        for( int i = 0; i < neighbors.size(); i++ )
        {
            if( agent.getObservedGroupHistory().get( neighbors.get( i ).getId() ).groupId == group.getId() )
            {
                X++;
            }
        }

        // calculate lambda
        lambda = _alpha
                + ( ( _beta * Math.pow( X, _q ) ) / ( Math.pow( _S, _q ) + Math.pow(
                        X, _q ) ) );
        lambda *= kValue( decision );

        decision.setProbability( lambda );
    }

    @Override
    public void calcCancelProb( Decision decision )
    {
        Agent agent = decision.getAgent();
        // probability to cancel
        double psiC = 0.0;

        // the number of agents currently in this group
        int X = 0;

        // calculate observed X value
        List<Agent> neighbors = agent.getNearestNeighbors();
        for( int i = 0; i < neighbors.size(); i++ )
        {
            if( agent.getObservedGroupHistory().get( neighbors.get( i ).getId() ).groupId == agent.getGroup().getId() )
            {
                X++;
            }
        }

        // calculate psiC
        if( ( (double) X / neighbors.size() ) >= ( agent.getCancelThreshold() ) )
        {
            // if threshold is reached then will not cancel
            psiC = 0;
        }
        else
        {
            psiC = _alphaC
                    + ( ( _betaC * Math.pow( X, _q ) ) / ( Math.pow( _S, _q ) + Math.pow(
                            X, _q ) ) );
            psiC *= 1 / kValue( decision );
        }

        decision.setProbability( psiC );
    }

    /**
     * Calculates the k value to modify the probabilities
     * 
     * @param decision The decision for which the k value needs to be calculated
     * @return The k value
     */
    private double kValue( Decision decision )
    {
        double k = 0;
        double x = 0;
        // if the decision is initiation then x = p
        if( decision.getDecisionType().equals( DecisionType.INITIATION ) )
        {
            x = decision.getAgent().getPersonalityTrait().getPersonality();
        }
        // else x = 1 - p
        else
        {
            x = 1 - decision.getAgent().getPersonalityTrait().getPersonality();
        }
        // the formula
        k = 2 / ( 1 + ( Math.exp( 10 * ( .5 - x ) ) ) );
        return k;
    }

    @Override
    public double[] getPreCalculatedFollowProbabilities()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double[] getPreCalculatedCancelProbabilities()
    {
        // TODO Auto-generated method stub
        return null;
    }
}