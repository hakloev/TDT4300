package no.ntnu.idi.tdt4300.arg.runnables;

import no.ntnu.idi.tdt4300.arg.apriori.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This is the main class of the association rule generator. It defines the input and output formats and you should
 * use it as a base for your 2nd assignment. Your assignment will be partly automatically evaluated, therefore keep
 * the input and output format of this class unchanged. Otherwise feel free to extend the predefined classes.
 *
 * @author tdt4300-undass@idi.ntnu.no
 */
public class AprioriAssociationRuleGenerator {

    public static final String SMALL_DATASET = "/data/smallDataset1.txt";
    public static final String LARGE_DATASET = "/data/supermarket.arff";

    /**
     * Loads the transaction from the text file.
     *
     * @param filepath relative file path
     * @return list of transactions as itemsets
     * @throws IOException signals that I/O error has occurred
     */
    public static ArrayList<ItemSet<String>> readFile(String filepath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(AprioriAssociationRuleGenerator.class
                .getResourceAsStream(filepath)));

        ArrayList<String> attributeNames = new ArrayList<String>();
        ArrayList<ItemSet<String>> itemSets = new ArrayList<ItemSet<String>>();

        String line = reader.readLine();
        while (line != null) {
            if (line.contains("#") || line.length() < 2) {
                line = reader.readLine();
                continue;
            }
            if (line.contains("attribute")) {
                int startIndex = line.indexOf("'");
                if (startIndex > 0) {
                    int endIndex = line.indexOf("'", startIndex + 1);
                    attributeNames.add(line.substring(startIndex + 1, endIndex));
                }

            } else {
                ItemSet<String> is = new ItemSet<String>();
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                int attributeCounter = 0;
                String itemSet = "";
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken().trim();
                    if (token.equalsIgnoreCase("t")) {
                        String attribute = attributeNames.get(attributeCounter);

                        itemSet += attribute + ",";
                        is.addItem(attribute);
                    }
                    attributeCounter++;
                }
                itemSets.add(is);
            }
            line = reader.readLine();
        }
        reader.close();

        return itemSets;
    }

    /**
     * The main method is accepting 3 command-line arguments.
     * <p/>
     * Arguments:
     * (small|large) <minsup> <minconf>
     * <p/>
     * Description:
     * small        Small dataset.
     * large        Large dataset.
     * <minsup>     Support threshold <0, 1>.
     * <minconf>    Confidence threshold <0, 1>.
     *
     * @param args array of command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Starting (dataset=" + args[0] + ", minsup=" + args[1] + ", minconf=" +  args[2] + ")...");

        // loading dataset (market basket transactions)
        List<ItemSet<String>> transactions = null;
        try {
            switch (args[0]) {
                case "small":
                    transactions = readFile(SMALL_DATASET);
                    break;
                case "large":
                    transactions = readFile(LARGE_DATASET);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // support and confidence threshold
        Double minSupport = Double.parseDouble(args[1]);
        Double minConfidence = Double.parseDouble(args[2]);

        // generating the association rules
        AbstractAprioriAlgorithm<String> aprioriAlgorithm = new Fkm1Fkm1Algorithm<String>(transactions);

        aprioriAlgorithm.generate(minSupport, minConfidence);

        // the results
        System.out.println("Generated frequent itemsets:\n" + aprioriAlgorithm.getFrequentItemSets());
        System.out.println("Generated " + aprioriAlgorithm.getRules().size() + " rules:\n" + aprioriAlgorithm.getRules());

        System.out.println("Finished :-)");
    }

}
