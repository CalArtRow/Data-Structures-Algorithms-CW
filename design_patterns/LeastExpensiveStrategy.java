public class LeastExpensiveStrategy implements ScoringStrategy {
    public int getScore(Product a)
    {
        // Score for product a is the negative of its cost, so that lower costs get higher scores 
        return -a.cost;
    }
}