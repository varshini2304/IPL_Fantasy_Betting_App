-- Database Migration Script: Allow NULL for winner_team_id in match_results table
-- This is required to support match draws where no winner exists
-- Run this script in your MySQL database before using the draw feature

USE ipl_fantasy;

-- Alter the match_results table to allow NULL for winner_team_id
ALTER TABLE match_results 
MODIFY COLUMN winner_team_id BIGINT NULL;

-- Verify the change
DESCRIBE match_results;
