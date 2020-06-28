package algorithm;

import java.security.SecureRandom;
import java.util.*;

/**
 * @author Haim Adrian
 * @since 28-Jun-20
 */
public class HuffmanCoding implements AlgorithmIfc {

    @Override
    public void execute() {
        String text = "aaaaaaaaaabbbbbbbbbbccccddddef";

        HuffmanTreeNode root = buildHuffmanTree(text);

        System.out.println("Huffman code generation:");
        System.out.println("The format is - [c, f(c)]: code,    where c = char, f(c) = frequency");
        root.print();
        System.out.println("Cost of the tree is B(T)=" + root.calculateCost(0));
        System.out.println("Tree:");
        System.out.println(root.toTreeString());
    }

    private HuffmanTreeNode buildHuffmanTree(String text) {
        HuffmanTreeNode root = null;
        Map<Character, Long> charFrequency = new HashMap<>();

        // Count the frequency of each character. O(n)
        for (char c : text.toCharArray()) {
            Character currKey = Character.valueOf(c);
            charFrequency.merge(currKey, Long.valueOf(1), (existingValue, newValue) -> Long.valueOf(existingValue.longValue() + newValue.longValue()));
        }

        // Create a minimum heap so we can poll two minimum elements in O(1).
        PriorityQueue<HuffmanTreeNode> minHeap = new PriorityQueue<HuffmanTreeNode>(charFrequency.size(),
                                                                                    Comparator.comparingLong(node -> node.count));
        // Add nodes to heap - O(logn)
        charFrequency.forEach((currChar, currFrequency) -> minHeap.add(new HuffmanTreeNode(currChar, currFrequency.longValue(), null, null)));

        if (minHeap.size() == 1) {
            root = minHeap.poll();
        } else {
            SecureRandom rand = new SecureRandom();

            // As long as the minimum heap is not empty, continue building the tree
            while (minHeap.size() > 1) {
                HuffmanTreeNode left = minHeap.poll();
                HuffmanTreeNode right = minHeap.poll();

                // Add some randomness in case two counts are equal
                if (left.count == right.count) {
                    System.out.println("Found two equal chars frequency, which means that a different tree can be built. Trying to swap them randomly.");
                    if (rand.nextBoolean()) {
                        HuffmanTreeNode temp = left;
                        left = right;
                        right = temp;
                    }
                }

                root = new HuffmanTreeNode(HuffmanTreeNode.NULL_CHAR, left.count + right.count, left, right);
                minHeap.add(root);
            }
        }

        return root;
    }


    private static class HuffmanTreeNode {
        static final Character NULL_CHAR = null;

        /** The character represented by this node */
        Character ch = NULL_CHAR;

        /** How many occurrences of our character exist in the input */
        long count;

        /** left node, '0' in binary code */
        HuffmanTreeNode left;

        /** left node, '1' in binary code */
        HuffmanTreeNode right;

        public HuffmanTreeNode(Character ch, long count, HuffmanTreeNode left, HuffmanTreeNode right) {
            this.ch = ch;
            this.count = count;
            this.left = left;
            this.right = right;
        }

        public void print() {
            print("");
        }

        private void print(String code) {
            if (ch != NULL_CHAR) {
                System.out.println(toString() + ": " + (code.isEmpty() ? "0" : code));
            }

            if (left != null) {
                left.print(code + "0");
            }

            if (right != null) {
                right.print(code + "1");
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            if (ch != NULL_CHAR) {
                sb.append((ch.charValue() == '\n' ? "\\n" : ch.charValue() == '\t' ? "\\t" : "" + ch.charValue())).append(", ");
            }

            sb.append(count).append("]");
            return sb.toString();
        }

        public long calculateCost(int depth) {
            // If it is a leaf node, that means it is a character
            if ((left == null) && (right == null)) {
                return Math.max(1, depth) * count;
            }

            return left.calculateCost(depth + 1) + right.calculateCost(depth + 1);
        }

        public String toTreeString() {
            StringBuilder buffer = new StringBuilder();
            toTreeString(buffer, "", "");
            return buffer.toString();
        }

        private void toTreeString(StringBuilder buffer, String prefix, String childrenPrefix) {
            buffer.append(prefix);
            buffer.append(ch == NULL_CHAR ? "(" + count + ")" : (ch.charValue() + " (" + count + ")"));
            buffer.append('\n');

            if (left != null) {
                left.toTreeString(buffer, childrenPrefix + "├─0─ ", childrenPrefix + "│    ");
                right.toTreeString(buffer, childrenPrefix + "└─1─ ", childrenPrefix + "     ");
            }
        }
    }
}

