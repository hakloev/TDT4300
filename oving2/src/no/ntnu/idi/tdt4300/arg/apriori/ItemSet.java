package no.ntnu.idi.tdt4300.arg.apriori;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Itemset and a predefined set of operations upon the itemset. The items are sorted in the itemset.
 *
 * @param <V> the type of items
 * @author tdt4300-undass@idi.ntnu.no
 */
public class ItemSet<V> implements Comparable<ItemSet<V>> {

    private SortedSet<V> items;

    /**
     * Creates a new empty instance of ItemSet.
     */
    public ItemSet() {
        items = new TreeSet<V>();
    }

    /**
     * Creates a new instance of ItemSet and adds the given items.
     *
     * @param items items to be added
     */
    public ItemSet(Set<V> items) {
        this.items = new TreeSet<V>(items);
    }

    /**
     * Creates a new instance of ItemSet and adds the given item.
     *
     * @param item item to be added
     */
    public ItemSet(V item) {
        this.items = new TreeSet<V>();
        this.items.add(item);
    }

    /**
     * Returns all items of the itemset.
     *
     * @return all items of the itemset
     */
    public Set<V> getItems() {
        return items;
    }

    /**
     * Returns first item of the itemset.
     *
     * @return first item of the itemset
     */
    public V first() {
        return items.first();
    }

    /**
     * Return the last item of the itemset.
     *
     * @return the last item of the itemset
     */
    public V last() {
        return items.last();
    }

    /**
     * Adds item to the itemset.
     *
     * @param v item to be added
     * @return
     */
    public boolean addItem(V v) {
        return items.add(v);
    }

    /**
     * Returns the number of items in the itemset.
     *
     * @return itemset size
     */
    public int size() {
        return items.size();
    }

    /**
     * Returns the union of this instance and the specified itemset.
     *
     * @param itemSet the itemset with which to union
     * @return the result of union
     */
    public ItemSet<V> union(ItemSet<V> itemSet) {
        Set<V> union = new LinkedHashSet<V>(getItems());
        union.addAll(itemSet.getItems());
        return new ItemSet<V>(union);
    }

    /**
     * Returns the union of this instance and the specified itemset.
     *
     * @param item
     * @return the result of union
     */
    public ItemSet<V> union(V item) {
        Set<V> union = new LinkedHashSet<V>(getItems());
        union.add(item);
        return new ItemSet<V>(union);
    }

    /**
     * Returns the intersection of this instance and the specified itemset.
     *
     * @param itemSet the itemset that should be intersected
     * @return the result of intersection
     */
    public ItemSet<V> intersection(ItemSet<V> itemSet) {
        Set<V> intersection = new HashSet<V>(getItems());
        intersection.retainAll(itemSet.getItems());
        return new ItemSet<V>(intersection);
    }

    /**
     * Returns the difference of this instance and the specified itemset.
     *
     * @param itemSet the items that the resulting itemset should not contain
     * @return the result of difference
     */
    public ItemSet<V> difference(ItemSet<V> itemSet) {
        Set<V> difference = new HashSet<V>(getItems());
        difference.removeAll(itemSet.getItems());
        return new ItemSet<V>(difference);
    }

    /**
     * Returns the difference of this instance and the specified item.
     *
     * @param item the item that the resulting itemset should not contain
     * @return the result of difference
     */
    public ItemSet<V> difference(V item) {
        Set<V> difference = new HashSet<V>(getItems());
        difference.remove(item);
        return new ItemSet<V>(difference);
    }

    @Override
    public boolean equals(Object o) {
        return ((ItemSet<V>) o).items.equals(items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    /**
     * The comparison is based only on the first items.
     *
     * @param o itemset to be compared
     * @return the value 0 if the first argument item is equal to this rule; a value less than 0 if this rule is
     * lexicographically or lengthwise less than the rule argument; and a value greater than 0 if this rule is
     * lexicographically or lengthwise greater than the rule argument
     */
    @Override
    public int compareTo(ItemSet<V> o) {
        return items.first().toString().compareTo(o.items.first().toString());
    }

    @Override
    public String toString() {
        return items.toString();
    }

}
