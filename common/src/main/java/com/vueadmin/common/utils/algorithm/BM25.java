package com.vueadmin.common.utils.algorithm;

import java.util.*;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/3 20:05
 **/
public class BM25 {
    private static double PARAM_K1 = 1.5;
    private static double PARAM_B = 0.75;
    private static double EPSILON = 0.25;

    private HashMap<String, Double> idfs;
    private ArrayList<Map<String, Integer>> docFreqs;
    private HashMap<String, Integer> nd;
    private int corpusSize;
    private double avgdl;
    private List<Integer> docLen;

    public BM25(List<List<String>> corpus) {
        corpusSize = corpus.size();
        docLen = new ArrayList<>();
        idfs = new HashMap<>();
        docFreqs = new ArrayList<>();
        nd = new HashMap<>();
        int numDoc = 0;
        // 图片中的单个标识在其图片所有标识出现的次数
        for (List<String> labels : corpus) {
            numDoc += labels.size();
            docLen.add(labels.size());
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
        avgdl = Double.valueOf(numDoc) / corpusSize;
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
        for (int i = 0; i < corpusSize; i++) {
            double score = getScore(query, i);
            scores.add(score);
        }
        return scores;
    }

    public double getScore(List<String> query, int index) {
        double score = 0;
        Map<String, Integer> freq = docFreqs.get(index);
        double k = PARAM_K1 * (1 - PARAM_B + PARAM_B * docLen.get(index) / avgdl);
//        double k = PARAM_K1;
        for (String word : query) {
            if (freq.containsKey(word)) {
                double df = freq.get(word);
                Double idf = idfs.get(word);
                // 计算单词与文档的相关性
                double sqd = df * (PARAM_K1 + 1) / (k + df);
                // 得分
                score += idf * sqd;
            }
        }
        return score;
    }

}
