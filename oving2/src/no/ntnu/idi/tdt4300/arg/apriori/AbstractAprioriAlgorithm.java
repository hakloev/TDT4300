package no.ntnu.idi.tdt4300.arg.apriori;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This class provides (or should provide) a skeletal implementation for the specific Apriori algorithms.
 *
 * @param <V> the type of items
 * @author tdt4300-undass@idi.ntnu.no
 */
public abstract class AbstractAprioriAlgorithm<V> {

    /**
     * Transactions.
     */
    protected List<ItemSet<V>> transactions;

    protected double minSupport;
    protected double minConfidence;

    /**
     * Here we store the frequent itemset.
     */
    protected HashMap<Integer, List<ItemSet<V>>> frequentItemSets;

    /**
     * Here we store the generated (final) association rules.
     */
    protected List<AssociationRule<V>> rules;

    /**
     * Constructor for use in the subclasses. Initializes the structures, sets the transactions and the support
     * and confidence thresholds to zero.
     *
     * @param transactions  transactions to be processed
     */
    public AbstractAprioriAlgorithm(List<ItemSet<V>> transactions) {
        this.transactions = transactions;
        this.minSupport = 0;
        this.minConfidence = 0;

        frequentItemSets = new HashMap<Integer, List<ItemSet<V>>>();
        rules = new LinkedList<AssociationRule<V>>();
    }

    /**
     * Generates frequent itemset and rules with support and confidence measures higher than or equal to the given
     * threshold. The result are available through the methods {@link AbstractAprioriAlgorithm#getFrequentItemSets()}
     * and {@link AbstractAprioriAlgorithm#getRules()}.
     *
     * @param minSupport support threshold
     * @param minConfidence confidence threshold
     */
    public abstract void generate(double minSupport, double minConfidence);

    /**
     * Returns the generated frequent itemsets.
     *
     * @return generated frequent itemsets
     */
    public HashMap<Integer, List<ItemSet<V>>> getFrequentItemSets() {
        return frequentItemSets;
    }

    /**
     * Returns the generated (final) association rules.
     *
     * @return generated association rules
     */
    public List<AssociationRule<V>> getRules() {
        return rules;
    }

}
