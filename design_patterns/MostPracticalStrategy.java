public class MostPracticalStrategy implements ScoringStrategy {
    public int getScore(Product a)
    {
        // Give score for product a as its practicality so that higher practicality gets higher scores
        return a.practicality;
    }
}

