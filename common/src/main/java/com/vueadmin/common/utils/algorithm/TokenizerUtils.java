package com.vueadmin.common.utils.algorithm;

import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.wltea.analyzer.lucene.IKTokenizer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/4 15:31
 **/
public class TokenizerUtils {

    public static List<String> lucene3Tokenizer(String query) {
        ArrayList<String> words = new ArrayList<>();
        IKTokenizer tokenizer = new IKTokenizer(new StringReader(query) , false);
        try {
            while(tokenizer.incrementToken()){
                TermAttribute termAtt = tokenizer.getAttribute(TermAttribute.class);
                words.add(termAtt.term());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

}
