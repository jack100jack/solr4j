package how2java;
import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;


public class SolrUtil {
    public static SolrClient client;
    private static String url;
    static {
        url = "http://localhost:8983/solr/how2java";
        client = new HttpSolrClient.Builder(url).build();
    }


    public static <T> boolean batchSaveOrUpdate(List<T> entities) throws SolrServerException, IOException {

        DocumentObjectBinder binder = new DocumentObjectBinder();
		int total = entities.size();
		int count=0;
        for (T t : entities) {
            SolrInputDocument doc = binder.toSolrInputDocument(t);
            client.add(doc);
            System.out.printf("������ݵ������У��ܹ�Ҫ��� %d ����¼����ǰ��ӵ�%d�� %n",total,++count);
        			}
        client.commit();
        return true;
    }
    public static QueryResponse query(String keywords,int startOfPage, int numberOfPage) throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery();
        query.setStart(startOfPage);
        query.setRows(numberOfPage);
        
        query.setQuery(keywords);
        QueryResponse rsp = client.query(query);
        return rsp;
    }
   

}