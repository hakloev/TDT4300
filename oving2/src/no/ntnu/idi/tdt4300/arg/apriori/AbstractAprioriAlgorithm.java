package no.ntnu.idi.tdt4300.arg.apriori;

import java.util.*;

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
     * Will use this as a temporary store for support values for given item sets
     */
    protected Map<ItemSet<V>, Double> supportValuesForItemsSets = new HashMap<ItemSet<V>, Double>();

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
     * Removes the candidate item sets that has support less than minsup.
     *
     * @param candidateItemSets item sets to be processed
     * @return list containing all item set candidates valid (greater or equal to minsup)
     */
    protected List<ItemSet<V>> pruneItemSets(List<ItemSet<V>> candidateItemSets) {
        List<ItemSet<V>> validCandidates = new LinkedList<ItemSet<V>>();
        for (ItemSet<V> itemSet : candidateItemSets) {
            if (supportValuesForItemsSets.get(itemSet) >= minSupport) validCandidates.add(itemSet);
        }
        return validCandidates;
    }


    /**
     * Generates the itemsets for k = 1 and adds the support number to the temporary storage.
     *
     * @return list containing all level 1 item sets
     */
    protected List<ItemSet<V>> getFirstLevelItemSets() {
        Set<ItemSet<V>> levelItemSets = new HashSet<ItemSet<V>>();
        for (ItemSet<V> transaction : transactions) {
            for (V item : transaction.getItems()) {
                ItemSet<V> itemSet = new ItemSet<V>();
                itemSet.addItem(item);
                levelItemSets.add(itemSet);
                calculateOccurrencesOfItemSetInTransactions(itemSet);
            }
        }
        return new LinkedList<ItemSet<V>>(levelItemSets);
    }

    /**
     * Calculates how many time an item set occurs in the transaction list and saves it to the temporary storage.
     *
     * @param itemSet item set to be processed
     * @return double telling how many times and item set occured
     */
    protected double calculateOccurrencesOfItemSetInTransactions(ItemSet<V> itemSet) {
//        System.out.println("Calculating occurrences of " + itemset);
        Double support = supportValuesForItemsSets.get(itemSet);
        if (support != null) return support;

        int itemSetOccurrences = 0;
        for (ItemSet<V> transaction : transactions) {
            if (transaction.intersection(itemSet).size() == itemSet.size()) itemSetOccurrences++;
        }
        supportValuesForItemsSets.put(itemSet, ((double) itemSetOccurrences) / transactions.size());
        return itemSetOccurrences;
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
