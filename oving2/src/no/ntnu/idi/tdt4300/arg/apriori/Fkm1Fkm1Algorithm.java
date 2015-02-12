package no.ntnu.idi.tdt4300.arg.apriori;

import java.util.List;

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

    @Override
    public void generate(double minSupport, double minConfidence) {
        // TODO: Your implementation...
        System.out.println("minSup: " + minSupport + " minConf: " + minConfidence);

    }



}
