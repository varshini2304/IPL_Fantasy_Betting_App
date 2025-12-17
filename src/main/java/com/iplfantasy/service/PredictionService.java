package com.iplfantasy.service;

import com.iplfantasy.entity.Match;
import com.iplfantasy.entity.Prediction;
import com.iplfantasy.entity.Team;
import com.iplfantasy.entity.User;

import java.util.List;

public interface PredictionService {

    Prediction createOrUpdate(User user, Match match, Team predictedTeam);

    void updateTossWinner(Prediction prediction, Team tossWinner);

    void updateTopScorer(Prediction prediction, String topScorer);

    void updateManOfTheMatch(Prediction prediction, String manOfTheMatch);

    void updateTotalRuns(Prediction prediction, Integer min, Integer max);

    List<Prediction> getUserPredictions(User user);

    List<Prediction> getMatchPredictions(Match match);

    void lockPredictions(Match match);

    void lockTossPredictions(Match match);

    void awardPoints(Match match);
}
