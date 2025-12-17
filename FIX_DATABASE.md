# Database Fix for Match Draw Feature

## Problem
The `match_results` table has `winner_team_id` set as `NOT NULL`, but we need it to allow `NULL` values for drawn matches.

## Solution
Run the following SQL command in your MySQL database:

### Option 1: Using MySQL Command Line
```bash
mysql -u root -p ipl_fantasy -e "ALTER TABLE match_results MODIFY COLUMN winner_team_id BIGINT NULL;"
```

### Option 2: Using MySQL Workbench or phpMyAdmin
1. Connect to your MySQL database
2. Select the `ipl_fantasy` database
3. Run this SQL command:
```sql
ALTER TABLE match_results MODIFY COLUMN winner_team_id BIGINT NULL;
```

### Option 3: Using MySQL Command Line (Interactive)
1. Open Command Prompt or PowerShell
2. Run: `mysql -u root -p`
3. Enter your password
4. Run these commands:
```sql
USE ipl_fantasy;
ALTER TABLE match_results MODIFY COLUMN winner_team_id BIGINT NULL;
EXIT;
```

## Verification
After running the command, verify it worked:
```sql
DESCRIBE match_results;
```
You should see `winner_team_id` with `Null: YES`

## After Running This
Restart your Tomcat server and try setting a match as a draw again.
