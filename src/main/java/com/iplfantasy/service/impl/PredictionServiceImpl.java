package com.iplfantasy.service.impl;

import com.iplfantasy.entity.Match;
import com.iplfantasy.entity.MatchResult;
import com.iplfantasy.entity.PointsHistory;
import com.iplfantasy.entity.Prediction;
import com.iplfantasy.entity.PredictionHistory;
import com.iplfantasy.entity.Team;
import com.iplfantasy.entity.User;
import com.iplfantasy.repository.MatchResultRepository;
import com.iplfantasy.repository.PointsHistoryRepository;
import com.iplfantasy.repository.PredictionHistoryRepository;
import com.iplfantasy.repository.PredictionRepository;
import com.iplfantasy.service.PredictionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PredictionServiceImpl implements PredictionService {

    @Autowired
    private PredictionRepository repo;

    @Autowired
    private PointsHistoryRepository pointsHistoryRepo;

    @Autowired
    private PredictionHistoryRepository predictionHistoryRepo;

    @Autowired
    private MatchResultRepository matchResultRepo;

    @Override
    public Prediction createOrUpdate(User user, Match match, Team predictedTeam) {

        if (match.getMatchStartTime() != null &&
                LocalDateTime.now().isAfter(match.getMatchStartTime())) {
            throw new RuntimeException("Prediction window closed! Match has already started.");
        }

        if (match.getTossTime() != null && match.getTossWinnerTeam() != null &&
                LocalDateTime.now().isAfter(match.getTossTime())) {
        }

        Prediction existing = repo.findByUserAndMatch(user, match);

        if (existing == null) {

            Prediction p = Prediction.builder()
                    .user(user)
                    .match(match)
                    .predictedTeam(predictedTeam)
                    .predictionTime(LocalDateTime.now())
                    .locked(false)
                    .pointsAwarded(0)
                    .build();

            repo.save(p);
            return p;
        }

        if (existing.isLocked()) {
            throw new RuntimeException("Prediction is locked!");
        }

        Team oldTeam = existing.getPredictedTeam();
        if (!oldTeam.equals(predictedTeam)) {
            PredictionHistory history = PredictionHistory.builder()
                    .prediction(existing)
                    .user(user)
                    .oldTeam(oldTeam)
                    .newTeam(predictedTeam)
                    .changeTime(LocalDateTime.now())
                    .changeReason("User updated prediction")
                    .build();
            predictionHistoryRepo.save(history);
        }

        existing.setPredictedTeam(predictedTeam);
        existing.setPredictionTime(LocalDateTime.now());
        repo.update(existing);
        return existing;
    }

    @Override
    public void updateTossWinner(Prediction prediction, Team tossWinner) {
        Team oldTossWinner = prediction.getPredictedTossWinner();

        if (oldTossWinner == null || !oldTossWinner.equals(tossWinner)) {
            PredictionHistory history = PredictionHistory.builder()
                    .prediction(prediction)
                    .user(prediction.getUser())
                    .oldTossWinner(oldTossWinner)
                    .newTossWinner(tossWinner)
                    .changeTime(LocalDateTime.now())
                    .changeReason("User updated toss winner prediction")
                    .build();
            predictionHistoryRepo.save(history);
        }

        prediction.setPredictedTossWinner(tossWinner);
        repo.update(prediction);
    }

    @Override
    public void updateTopScorer(Prediction prediction, String topScorer) {
        String oldTopScorer = prediction.getPredictedTopScorer();

        if (oldTopScorer == null || !oldTopScorer.equals(topScorer)) {
            PredictionHistory history = PredictionHistory.builder()
                    .prediction(prediction)
                    .user(prediction.getUser())
                    .oldTopScorer(oldTopScorer)
                    .newTopScorer(topScorer)
                    .changeTime(LocalDateTime.now())
                    .changeReason("User updated top scorer prediction")
                    .build();
            predictionHistoryRepo.save(history);
        }

        prediction.setPredictedTopScorer(topScorer);
        repo.update(prediction);
    }

    @Override
    public void updateManOfTheMatch(Prediction prediction, String manOfTheMatch) {
        String oldMoM = prediction.getPredictedManOfTheMatch();

        if (oldMoM == null || !oldMoM.equals(manOfTheMatch)) {
            PredictionHistory history = PredictionHistory.builder()
                    .prediction(prediction)
                    .user(prediction.getUser())
                    .oldManOfTheMatch(oldMoM)
                    .newManOfTheMatch(manOfTheMatch)
                    .changeTime(LocalDateTime.now())
                    .changeReason("User updated Man of the Match prediction")
                    .build();
            predictionHistoryRepo.save(history);
        }

        prediction.setPredictedManOfTheMatch(manOfTheMatch);
        repo.update(prediction);
    }

    @Override
    public void updateTotalRuns(Prediction prediction, Integer min, Integer max) {
        Integer oldMin = prediction.getPredictedTotalRunsMin();
        Integer oldMax = prediction.getPredictedTotalRunsMax();

        boolean changed = false;
        if (oldMin == null && min != null)
            changed = true;
        else if (oldMin != null && !oldMin.equals(min))
            changed = true;
        else if (oldMax == null && max != null)
            changed = true;
        else if (oldMax != null && !oldMax.equals(max))
            changed = true;

        if (changed) {
            PredictionHistory history = PredictionHistory.builder()
                    .prediction(prediction)
                    .user(prediction.getUser())
                    .oldTotalRunsMin(oldMin)
                    .oldTotalRunsMax(oldMax)
                    .newTotalRunsMin(min)
                    .newTotalRunsMax(max)
                    .changeTime(LocalDateTime.now())
                    .changeReason("User updated total runs range prediction")
                    .build();
            predictionHistoryRepo.save(history);
        }

        prediction.setPredictedTotalRunsMin(min);
        prediction.setPredictedTotalRunsMax(max);
        repo.update(prediction);
    }

    @Override
    public List<Prediction> getUserPredictions(User user) {
        return repo.findByUser(user);
    }

    @Override
    public List<Prediction> getMatchPredictions(Match match) {
        return repo.findByMatch(match);
    }

    @Override
    public void lockPredictions(Match match) {
        for (Prediction p : repo.findByMatch(match)) {
            p.setLocked(true);
            repo.update(p);
        }
    }

    @Override
    public void lockTossPredictions(Match match) {
     
    }

    @Override
    public void awardPoints(Match match) {

        if (match.getWinnerTeam() == null)
            return;

        MatchResult result = matchResultRepo.findByMatch(match);
        if (result == null) {
            lockPredictions(match);
            return;
        }

        Team actualWinner = result.getWinnerTeam();
        Team actualTossWinner = result.getTossWinner();
        String actualMoM = result.getManOfTheMatch();
        String actualTopScorer = result.getTopScorer();

        List<Prediction> preds = repo.findByMatch(match);

        for (Prediction p : preds) {
            int totalPoints = 0;
            int winnerPoints = 0;
            int tossPoints = 0;
            int momPoints = 0;
            int topScorerPoints = 0;

            if (p.getPredictedTeam() != null && actualWinner != null &&
                    p.getPredictedTeam().getTeamId().equals(actualWinner.getTeamId())) {
                winnerPoints = 10;
                totalPoints += winnerPoints;
                System.out.println("User " + p.getUser().getUserId() + ": Match Winner correct! +10 points");
            }

            if (actualTossWinner != null && p.getPredictedTossWinner() != null &&
                    p.getPredictedTossWinner().getTeamId().equals(actualTossWinner.getTeamId())) {
                tossPoints = 2;
                totalPoints += tossPoints;
                System.out.println("User " + p.getUser().getUserId() + ": Toss Winner correct! +2 points");
            }

            if (actualMoM != null && !actualMoM.trim().isEmpty() &&
                    p.getPredictedManOfTheMatch() != null &&
                    !p.getPredictedManOfTheMatch().trim().isEmpty()) {
                if (p.getPredictedManOfTheMatch().trim().equalsIgnoreCase(actualMoM.trim())) {
                    momPoints = 3;
                    totalPoints += momPoints;
                    System.out.println("User " + p.getUser().getUserId() + ": MoM correct! +3 points");
                }
            }

            if (actualTopScorer != null && !actualTopScorer.trim().isEmpty() &&
                    p.getPredictedTopScorer() != null &&
                    !p.getPredictedTopScorer().trim().isEmpty()) {
                if (p.getPredictedTopScorer().trim().equalsIgnoreCase(actualTopScorer.trim())) {
                    topScorerPoints = 3;
                    totalPoints += topScorerPoints;
                    System.out.println("User " + p.getUser().getUserId() + ": Top Scorer correct! +3 points");
                }
            }

            if (totalPoints < 0) {
                totalPoints = 0;
            }

            System.out.println("User " + p.getUser().getUserId() + " total points: " + totalPoints +
                    " (Winner:" + winnerPoints + " Toss:" + tossPoints + " MoM:" + momPoints + " TopScorer:"
                    + topScorerPoints + ")");

            p.setPointsAwarded(totalPoints);
            p.setLocked(true);
            repo.update(p);

            PointsHistory history = PointsHistory.builder()
                    .user(p.getUser())
                    .match(match)
                    .points(totalPoints)
                    .timestamp(LocalDateTime.now())
                    .build();

            pointsHistoryRepo.save(history);
        }
    }
}
