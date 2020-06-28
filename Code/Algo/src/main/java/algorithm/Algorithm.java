package algorithm;

import java.lang.reflect.InvocationTargetException;

/**
 * Lists all algorithms in the application
 * @author Haim Adrian
 * @since 26-Jun-20
 */
public enum Algorithm {
    exit("Exit", null),
    majorityElement("Majority Element", MajorityElement.class),
    kmpMatcher("KMP-Matcher", KMP.class),
    suffixTrie("Suffix-Trie", SuffixTrie.class),
    millerRabin("Miller-Rabin", MillerRabin.class),
    repeatedSquares("Method of repeated squares", RepeatedSquares.class),
    huffmanCoding("Huffman-Coding", HuffmanCoding.class),
    unknown(null, null);

    private final String title;
    private final Class<? extends AlgorithmIfc> implementation;

    Algorithm(String title, Class<? extends AlgorithmIfc> implementation) {
        this.title = title;
        this.implementation = implementation;
    }

    public String getTitle() {
        return title;
    }

    @SuppressWarnings("unchecked")
    public <T extends AlgorithmIfc> T newInstance() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        T algorithm = (T)implementation.getDeclaredConstructor().newInstance();
        return algorithm;
    }
}
