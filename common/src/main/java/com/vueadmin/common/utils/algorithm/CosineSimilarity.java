package com.vueadmin.common.utils.algorithm;

import java.util.*;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/4 17:46
 **/
public class CosineSimilarity {

    private static double EPSILON = 0.25;
    private static double PARAM_A = 0.45;

    private HashMap<String, Double> idfs;
    private ArrayList<Map<String, Integer>> docFreqs;
    private HashMap<String, Integer> nd;
    private int corpusSize;
    private List<List<String>> corpus;

    public CosineSimilarity(List<List<String>> corpus) {
        this.corpus = corpus;
        corpusSize = corpus.size();
        idfs = new HashMap<>();
        docFreqs = new ArrayList<>();
        nd = new HashMap<>();
        // 图片中的单个标识在其图片所有标识出现的次数
        for (List<String> labels : corpus) {
            HashMap<String, Integer> frequencies = new HashMap<>();
            for (String label : labels) {
                Integer count = frequencies.get(label);
                if (count == null) {
                    frequencies.put(label, 1);
                    continue;
                }
                frequencies.put(label, count + 1);
            }
            docFreqs.add(frequencies);
        }
        // 标识在整个图片库出现的次数（一张图片只能算一次）
        for (Map<String, Integer> freq : docFreqs) {
            Iterator<Map.Entry<String, Integer>> entries = freq.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, Integer> entry = entries.next();
                Integer count = nd.get(entry.getKey());
                if (count == null) {
                    nd.put(entry.getKey(), 1);
                    continue;
                }
                nd.put(entry.getKey(), count + 1);
            }
        }
        // 计算单词权重
        ArrayList<String> negativeIdfs = new ArrayList<>();
        Iterator<Map.Entry<String, Integer>> entries = nd.entrySet().iterator();
        double idfSum = 0;
        double avgIdf = 0;
        while (entries.hasNext()) {
            Map.Entry<String, Integer> entry = entries.next();
            Integer df = entry.getValue();
            double idf = Math.log(corpusSize - df + 0.5) - Math.log(df + 0.5);
            idfs.put(entry.getKey(), idf);
            idfSum += idf;
            if (idf < 0) {
                negativeIdfs.add(entry.getKey());
            }
        }
        avgIdf = idfSum / idfs.size();
        double eps = EPSILON * avgIdf;
        for (String label : negativeIdfs) {
            idfs.put(label, eps);
        }
    }

    public List<Double> getScores(List<String> query) {
        ArrayList<Double> scores = new ArrayList<>();
        for (int i=0; i<corpusSize; i++) {
            double score = getScore(query, i);
            scores.add(score);
        }
        return scores;
    }

    public double getScore(List<String> query, int index) {
        List<String> labels = corpus.get(index);
        double score = 0;
        // 建立向量
        LinkedHashMap<String, Double> sentence1 = new LinkedHashMap<>();
        for (String word : query) {
            sentence1.put(word, 0.0);
        }
        LinkedHashMap<String, Double> sentence2 = new LinkedHashMap<>();
        sentence2.putAll(sentence1);
        for (String label : labels) {
            Double originalNum = sentence1.get(label);
            if (originalNum == null) continue;
            Double addNum = idfs.get(label);
            // IDF 以文档库为标准，可不用处理addNum
//            addNum = addNum == null ? PARAM_A : addNum;
            sentence1.put(label, originalNum + addNum);
        }
        for (String word : query) {
            Double originalNum = sentence2.get(word);
            // 比较的向量元素以 query 为标准，可不处理
//            if (originalNum == null) continue;
            Double addNum = idfs.get(word);
            addNum = addNum == null ? PARAM_A : addNum;
            sentence2.put(word, originalNum + addNum);
        }
        // 计算向量的余弦
        Object[] objects = sentence1.values().toArray();
        Double[] values1 = Arrays.copyOf(objects, objects.length, Double[].class);
        objects = sentence2.values().toArray();
        Double[] values2 = Arrays.copyOf(objects, objects.length, Double[].class);
        double xy = 0;
        double xx = 0;
        double yy = 0;
        for (int i = 0; i < values1.length; i++) {
            xy += values1[i] * values2[i];
            xx += values1[i] * values1[i];
            yy += values2[i] * values2[i];
        }
        score = xy / (Math.sqrt(xx) * Math.sqrt(yy));
        if (Double.isNaN(score)) {
            score = 0;
        }
        return score;
    }

}
