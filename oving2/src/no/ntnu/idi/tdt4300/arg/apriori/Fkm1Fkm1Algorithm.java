package no.ntnu.idi.tdt4300.arg.apriori;

import java.util.*;

/**
 * The class implementing the Apriori algorithm using the "F_k-1 x F_k-1" method.
 *
 * @param <V> item type
 * @author Håkon Ødegård Løvdal and Aleksander Skraastad.
 */
public class Fkm1Fkm1Algorithm<V> extends AbstractAprioriAlgorithm<V> {


    public Fkm1Fkm1Algorithm(List<ItemSet<V>> transactions) {
        super(transactions);
    }

    /**
     * Method to find level N item sets using "F_k-1 x F_k-1" method.
     * Similar to apriori-gen in the pseudocode
     *
     * @param itemSets list containing all item set candidates from level N - 1
     * @return list containing all valid item sets for level N
     */
    private List<ItemSet<V>> generateLevelNItemSets(List<ItemSet<V>> itemSets) {
        Set<ItemSet<V>> validCandidates = new HashSet<ItemSet<V>>();
        // For each of the item sets in the list
        for (int i = 0; i < itemSets.size(); i++) {
            ItemSet<V> setOne = itemSets.get(i);
            // Find the next item set and check against setOne
            for (int j = i + 1; j <itemSets.size(); j++) {
                ItemSet<V> setTwo = itemSets.get(j);
                ItemSet<V> differenceBetweenSets = setTwo.difference(setOne);
                // Check elements in difference set
                for (V element: differenceBetweenSets.getItems()) {
                    ItemSet<V> possibleItemSet = new ItemSet<V>();
                    possibleItemSet.addItem(element);
                    ItemSet<V> union = setOne.union(possibleItemSet);
//                    System.out.println(setOne);
//                    System.out.println(union);
                    // If union's size ain't equal setOne + 1 the first n-1 items doesn't match
                    if (union.size() != setOne.size() + 1) continue;

                    // To save time later on, calculate support instantly
                    calculateOccurrencesOfItemSetInTransactions(union);
                    validCandidates.add(union);
                }
            }
        }
        return new LinkedList<ItemSet<V>>(validCandidates);
    }

    /**
     * Main method for generating all frequent item sets and association rules
     *
     * @param minSupport support threshold
     * @param minConfidence confidence threshold
     */
    @Override
    public void generate(double minSupport, double minConfidence) {
        super.minSupport = minSupport;
        super.minConfidence = minConfidence;
        List<ItemSet<V>> firstLevelItemSets = getFirstLevelItemSets();
        System.out.println("\nLevel 1 item sets: "  + firstLevelItemSets);
        List<ItemSet<V>> validItemSetsAtLevel = pruneItemSets(firstLevelItemSets);
        System.out.println("Level 1 item sets after pruning: " + validItemSetsAtLevel);
        System.out.println("\nStarting process to generate frequent item sets for levels > 1.");
        frequentItemSets.put(1, validItemSetsAtLevel);
        // Continue with k > 1 and create item sets
        int currentLevel = 1;
        do {
            currentLevel++;
            System.out.println("Generating level " + currentLevel + " itemsets.");
            List<ItemSet<V>> candidateItemSetsAtLevelN = generateLevelNItemSets(validItemSetsAtLevel);
            validItemSetsAtLevel = pruneItemSets(candidateItemSetsAtLevelN);
            System.out.println("Level " + currentLevel + " item sets after pruning: " + validItemSetsAtLevel + "\n");
            if (validItemSetsAtLevel.size() == 0) {
                System.out.println("Got all candidate item sets");
                break;
            }
            frequentItemSets.put(currentLevel, validItemSetsAtLevel);
        } while (validItemSetsAtLevel.size() != 0);
        System.out.println("Ending process to generate frequent item sets for levels > 1.\n");

        System.out.println("Starting process to generate rules");
        // Generating rules from all item sets larger than 1
        for (int i = 2; i <= frequentItemSets.size(); i++) {
            List<ItemSet<V>> levelSet = frequentItemSets.get(i);
            for (ItemSet<V> itemSet : levelSet) {
                for (V item : itemSet.getItems()) {
                    ItemSet<V> antecedent = itemSet.difference(item);
                    ItemSet<V> consquent = new ItemSet<V>(item);
                    AssociationRule<V> rule = new AssociationRule<V>(antecedent, consquent);
                    Double supportWhole = supportValuesForItemsSets.get(antecedent.union(consquent));
                    Double supportRule = supportValuesForItemsSets.get(antecedent);
                    if ((supportWhole != null) && (supportRule != null)) {
                        rule.setSupport(supportWhole);
                        rule.setConfidence(supportWhole / supportRule);
                    }
                    rules.add(rule);
;                }
            }
        }
        System.out.println("Ending process to generate rules");
    }
}
