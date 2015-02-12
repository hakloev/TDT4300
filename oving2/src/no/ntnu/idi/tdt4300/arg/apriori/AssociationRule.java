package no.ntnu.idi.tdt4300.arg.apriori;

/**
 * Association rule "antecedent -> consequent".
 *
 * @author tdt4300-undass@idi.ntnu.no
 */
public class AssociationRule<V> implements Comparable<AssociationRule<V>> {

    private ItemSet<V> antecedent;
    private ItemSet<V> consequent;

    private double support;
    private double confidence;

    /**
     * Creates a new instance of AssociationRule with the given {@code antecedent} and {@code consequent}
     * ("antecedent -> consequent"). The support and confidence is set to 0 by default.
     *
     * @param antecedent the antecedent of the rule
     * @param consequent the consequent of the rule
     */
    public AssociationRule(ItemSet<V> antecedent, ItemSet<V> consequent) {
        this.antecedent = antecedent;
        this.consequent = consequent;

        support = 0;
        confidence = 0;
    }

    /**
     * Returns the antecedent of the rule.
     *
     * @return antecedent of the rule
     */
    public ItemSet<V> getAntecedent() {
        return antecedent;
    }

    /**
     * Sets the antecedent of the rule.
     *
     * @param antecedent the antecedent to be set
     */
    public void setAntecedent(ItemSet<V> antecedent) {
        this.antecedent = antecedent;
    }

    /**
     * Return the consequent of the rule.
     *
     * @return consequent of the rule
     */
    public ItemSet<V> getConsequent() {
        return consequent;
    }

    /**
     * Sets the consequent of the rule.
     *
     * @param consequent the consequent to be set
     */
    public void setConsequent(ItemSet<V> consequent) {
        this.consequent = consequent;
    }

    /**
     * Returns the support of the rule.
     *
     * @return support of the rule
     */
    public double getSupport() {
        return support;
    }

    /**
     * Sets the support of the rule.
     *
     * @param support the support value to be set
     */
    public void setSupport(double support) {
        this.support = support;
    }

    /**
     * Returns the confidence of the rule.
     *
     * @return the confidence of the rule
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * Sets the confidence of the rule.
     *
     * @param confidence the confidence value to be set
     */
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    /**
     * Returns all items of the rule as an itemset. In other words, it returns the result of antecedent and
     * consequent unification.
     *
     * @return all items of the rule as an itemset
     */
    public ItemSet<V> getAllItems() {
        return antecedent.union(consequent);
    }

    /**
     * Returns the rule as a string in the format "{antecedent} -> {consequent}".
     *
     * @return the rule as string
     */
    public String getRuleBodyAsString() {
        return "{" + antecedent + "} -> {" + consequent + "}";
    }

    /**
     * Returns the number of all items in the rule, i.e. count of items in the antecedent plus the count of items
     * in the consequent.
     *
     * @return number of all items in the rule
     */
    public int size() {
        return antecedent.size() + consequent.size();
    }

    /**
     * Returns the rule and its support and confidence values as a string.
     *
     * @return the rule and its support and confidence values as a string
     */
    @Override
    public String toString() {
        return getRuleBodyAsString() + ", confidence = " + confidence
                + ", support = " + support;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return toString().equals(((AssociationRule<V>) o).toString());
    }

    /**
     * Compares rule length, the number of items in the rule antecedent and the alphabetical order of the
     * items.
     *
     * @param o association rule to be compared
     * @return the value 0 if the argument rule is equal to this rule; a value less than 0 if this rule is
     * lexicographically or lengthwise less than the rule argument; and a value greater than 0 if this rule is
     * lexicographically or lengthwise greater than the rule argument
     */
    @Override
    public int compareTo(AssociationRule<V> o) {
        int ruleLength = size() - o.size();
        int itemsetALength = antecedent.size() - o.getAntecedent().size();

        if (ruleLength != 0) {
            return ruleLength;
        } else {
            if (itemsetALength != 0) {
                return itemsetALength;
            }
        }

        return toString().compareTo(o.toString());
    }

}