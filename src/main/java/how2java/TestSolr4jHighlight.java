
package how2java;

import java.io.IOException;
 
import org.apache.solr.client.solrj.SolrServerException;
 
public class TestSolr4jHighlight {
 
    public static void main(String[] args) throws SolrServerException, IOException {
        //������ѯ��ѯ
        SolrUtilHighlight.queryHighlight("name:�ֻ�");
         
    }
 
}