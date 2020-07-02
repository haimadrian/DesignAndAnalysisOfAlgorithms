package algorithm;

import util.ActionResult;

import java.util.*;

/**
 * @author Haim Adrian
 * @since 27-Jun-20
 */
public class SuffixTrie implements AlgorithmIfc {

    public static final Character END_CHAR = Character.valueOf('$');
    //private String text = KMP.getNoaKilaLyrics();
    private String text = "bananabanana$";

    @Override
    public void execute() {
        // Arrange
        text = readLine("Enter text to build a suffix-trie for: (for example: bananabanana)");
        if (!text.endsWith("$")) {
            text += "$";
        }

        // First build a tree with a node for each character
        // Build and compression are very inefficient but nvm.. Assume it is linear
        SuffixTreeNode root = buildTree();

        // You can print the tree here in case you need to see the node-per-character structure
        System.out.println("Tree before compression:");
        System.out.println(root.toTreeString());

        // Second, compress the tree. (Join nodes)
        compressTree(root);

        // Output
        System.out.println("Text is: " + System.lineSeparator() + text);
        System.out.println("Tree is: ");
        System.out.println(root.toTreeString());
        System.out.println("|T| = " + text.length());

        if (readYesNo("Would you like to enter a pattern to lookup for? (y/n)")) {
            //String pattern = "\tBangin' on my drum\n\tBangin' on my drum";
            String pattern = readLine("Enter pattern to lookup for in the tree: (for example: ana)");
            ActionResult<List<Integer>> results = find(root, pattern);

            System.out.println("Pattern: " + System.lineSeparator() + "\"" + pattern + "\"");
            System.out.println("|P| = " + pattern.length());
            System.out.println(results.getStepsCount() + " steps took to find all occurrences of pattern in text - O(|P|)");
            System.out.println("Occurrences of pattern in text are at: " + results.getResult());
        }

        System.out.println("Amount of different characters (children of root node): " + root.children.size());
        System.out.println("Amount of suffixes: " + (text.length() - 1));
    }

    /**
     * Search a pattern within a suffix-trie, as you'll be able to see in the results, this is way faster (way less operations)
     * than the KMP algorithm, as it takes O(|P|) only, and not O(|T|+|P|) like KMP.<br/>
     * Remember that this algorithm is fantastic for static text
     * @param root Root node of a suffix-trie
     * @param pattern The pattern to search
     * @return A list containing all occurrences (indexes) of the pattern in the text represented by the specified suffix-trie
     */
    private ActionResult<List<Integer>> find(SuffixTreeNode root, String pattern) {
        ActionResult<List<Integer>> result = new ActionResult<>();
        List<Integer> occurrences = new ArrayList<>(); // Keep all occurrences of pattern in text.

        if (pattern.length() > 0) {
            SuffixTreeNode node = root.children.get(Character.valueOf(pattern.charAt(0)));
            if (node != null) {
                findInner(node, pattern, occurrences, result);
            }
        }

        result.setResult(occurrences);
        return result;
    }

    /**
     * Same as the other find, but here we do it recursively to traverse children
     * @param node
     * @param pattern
     * @param occurrences
     * @param result
     */
    private void findInner(SuffixTreeNode node, String pattern, List<Integer> occurrences, ActionResult<List<Integer>> result) {
        if (node == null) {
            return;
        }

        for (int i = 0; i < pattern.length(); i++) {
            result.countStep();
            if (i > (node.end - node.begin)) {
                // In case we have reached end of current node, go to the relevant child
                SuffixTreeNode childToContinueFrom = node.children.get(Character.valueOf(pattern.charAt(i)));
                findInner(childToContinueFrom, pattern.substring(i), occurrences, result);
                return;
            } else if (pattern.charAt(i) != text.charAt(node.begin + i)) {
                // When pattern does not match the string, we exit without collecting results
                return;
            }
        }

        // If we have arrived to the end of the pattern, collect occurrences
        collectOccurrences(node, occurrences);
    }

    /**
     * When we end matching the pattern within a node then all suffix positions from this node and its children
     * are occurrences that we should collect.
     * @param node The node to collect occurrences from
     * @param occurrences Where to put the occurrences we have collected
     */
    private void collectOccurrences(SuffixTreeNode node, List<Integer> occurrences) {
        if (node.suffixPos != -1) {
            occurrences.add(Integer.valueOf(node.suffixPos));
        }

        node.children.values().forEach(child -> collectOccurrences(child, occurrences));
    }

    /**
     * This is phase two. After we have a tree with a node for each single character, we
     * compress it such that we will merge nodes with a single child and save some memory.
     * @param tree A tree that was built by {@link #buildTree(String)}
     */
    private void compressTree(SuffixTreeNode tree) {
        compressTreeInner(tree);

        // Now calculate the suffix position for leaf nodes
        tree.children.values().forEach(value -> fillInSuffixPos(value, 0));
    }

    /**
     * An internal recursive method to merge nodes with a single child into one node.
     * @param tree The tree to compress.
     */
    private void compressTreeInner(SuffixTreeNode tree) {
        if (tree.children.size() == 1) {
            SuffixTreeNode child = tree.children.values().stream().findFirst().get();

            // If the child is not '$', compress it recursively
            if (!tree.children.containsKey(END_CHAR)) {
                compressTreeInner(child);
                tree.end = child.end;
                tree.children = child.children;
            }
        } else {
            // Do not modify root's values
            if (tree.end != -1) {
                tree.end = tree.begin; // index so -1
            }

            tree.children.forEach((key, value) -> {
                if (!key.equals(END_CHAR)) {
                    compressTreeInner(value);
                }
            });
        }
    }

    /**
     * After compressing a tree we traverse it from top to bottom to count how many characters there are in a
     * suffix until we meet a $, so we can fill in the suffixPos using that information.
     * @param tree The tree to fill the suffixPos of its nodes
     * @param parentLength This is used recursively, so we can count the length of each node and then reduce it from text length
     */
    private void fillInSuffixPos(SuffixTreeNode tree, int parentLength) {
        if (parentLength == text.length() - 1) {
            return;
        }

        int currNodeLength = tree.end - tree.begin + 1;

        SuffixTreeNode endNode = tree.children.get(END_CHAR);
        if (endNode != null) {
            tree.suffixPos = text.length() - 1 - parentLength - currNodeLength;
            tree.children.remove(END_CHAR);
        }

        tree.children.values().forEach(value -> fillInSuffixPos(value, parentLength + currNodeLength));
    }

    /**
     * Main entry for building a suffix-trie, for the first phase where we have a node for each character.<br/>
     * This is based on the exercises and not the real implementation. The real implementation would have added
     * suffixes in compressed structure in the first place, and not the very risky implementation we use here.
     * @return The root node
     */
    private SuffixTreeNode buildTree() {
        SuffixTreeNode root = new SuffixTreeNode(-1, -1);

        List<String> suffixes = getAllSuffixes();
        for (String suffix : suffixes) {
            insertSuffixIntoTree(root, suffix);
        }

        return root;
    }

    /**
     * Get a list containing all suffixes of some text
     * @return A list containing all suffixes for a suffix-trie
     */
    private List<String> getAllSuffixes() {
        String validatedText = text.endsWith("$") ? text : (text + "$");
        List<String> suffixes = new ArrayList<>(validatedText.length() - 1); // Do not make a suffix for '$'

        for (int i = 0; i < text.length() - 1; i++) {
            suffixes.add(text.substring(i));
        }

        return suffixes;
    }

    /**
     * This method is used by the {@link #buildTree(String)} method so we will insert a specified suffix
     * into a node recursively until the leaf node: $.<br/>
     * This is the first part of building a suffix tree, where we have a separate node for each character.
     * @param node The node to insert a suffix to
     * @param text The full text, we use it to get the index of the suffix, so we can set the 'begin' and 'end' values
     * @param suffix The suffix to insert
     */
    private void insertSuffixIntoTree(SuffixTreeNode node, String suffix) {
        node.children.compute(Character.valueOf(suffix.charAt(0)), (key, value) -> {
            if (value == null) {
                int begin = text.lastIndexOf(suffix);
                int end = begin + suffix.length() - 1;
                if (!suffix.equals("$")) {
                    end--;
                }

                value = new SuffixTreeNode(begin, end);
            }

            // As long as we have not arrived to the end sign, go ahead
            if (!suffix.equals("$")) {
                insertSuffixIntoTree(value, suffix.substring(1));
            }

            return value;
        });
    }

    private static class SuffixTreeNode {
        /** Begin index of current node - the part in the text that this node represents */
        int begin;

        /** End index of current node - the part in the text that this node represents */
        int end;

        /** When current node is a leaf node, this member holds the index of the suffix (beginning index) that this leaf represents */
        int suffixPos = -1;

        /** All children nodes of current node, we map by their beginning character to get them in O(1) while searching */
        Map<Character, SuffixTreeNode> children = new HashMap<>();

        public SuffixTreeNode(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public String toString() {
            if (begin >= 0) {
                return "[" + begin + ", " + end + "]" + (suffixPos != -1 ? (" (" + suffixPos + ")") : "");
            } else {
                return "root";
            }
        }

        public String toTreeString() {
            StringBuilder buffer = new StringBuilder();
            toTreeString(buffer, "", "");
            return buffer.toString();
        }

        private void toTreeString(StringBuilder buffer, String prefix, String childrenPrefix) {
            buffer.append(prefix);
            buffer.append(toString());
            buffer.append('\n');

            for (Iterator<Map.Entry<Character, SuffixTreeNode>> it = children.entrySet().iterator(); it.hasNext();) {
                Map.Entry<Character, SuffixTreeNode> next = it.next();
                String ch = (next.getKey().charValue() == '\n' ? "\\n" : next.getKey().charValue() == '\t' ? "\\t" : "" + next.getKey());
                if (it.hasNext()) {
                    next.getValue().toTreeString(buffer, childrenPrefix + "├──" + ch + "── ", childrenPrefix + "│      ");
                } else {
                    next.getValue().toTreeString(buffer, childrenPrefix + "└──" + ch + "── ", childrenPrefix + "       ");
                }
            }
        }
    }
}

