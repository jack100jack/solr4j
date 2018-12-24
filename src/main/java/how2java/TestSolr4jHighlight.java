
package how2java;

import java.io.IOException;
 
import org.apache.solr.client.solrj.SolrServerException;
 
public class TestSolr4jHighlight {
 
    public static void main(String[] args) throws SolrServerException, IOException {
        //高亮查询查询
        SolrUtilHighlight.queryHighlight("name:手机");
         
    }
 
}